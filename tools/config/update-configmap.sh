###########################################################
#ENV VARS
###########################################################
envValue=$1
APP_NAME=$2
GRAD_NAMESPACE=$3
COMMON_NAMESPACE=$4
BUSINESS_NAMESPACE=$5
SPLUNK_TOKEN=$6
APP_LOG_LEVEL=$7
CLIENT_SECRET_NAME=grad-graduation-report-api-client-secret

SPLUNK_URL="gww.splunk.educ.gov.bc.ca"
FLB_CONFIG="[SERVICE]
   Flush        1
   Daemon       Off
   Log_Level    info
   HTTP_Server   On
   HTTP_Listen   0.0.0.0
   Parsers_File parsers.conf
[INPUT]
   Name   tail
   Path   /mnt/log/*
   Exclude_Path *.gz,*.zip
   Parser docker
   Mem_Buf_Limit 20MB
   Buffer_Max_Size 4MB
[FILTER]
   Name record_modifier
   Match *
   Record hostname \${HOSTNAME}
[OUTPUT]
   Name   stdout
   Match  absolutely_nothing_at_all
   Log_Level    off
[OUTPUT]
   Name  splunk
   Match *
   Host  $SPLUNK_URL
   Port  443
   TLS         On
   TLS.Verify  Off
   Message_Key $APP_NAME
   Splunk_Token $SPLUNK_TOKEN
"
PARSER_CONFIG="
[PARSER]
    Name        docker
    Format      json
"
###########################################################
#Setup for config-maps
###########################################################
echo Creating config map "$APP_NAME"-config-map
oc create -n "$GRAD_NAMESPACE"-"$envValue" configmap "$APP_NAME"-config-map \
 --from-literal=APP_LOG_LEVEL="$APP_LOG_LEVEL" \
 --from-literal=GRAD_STUDENT_API="http://educ-grad-student-api.$GRAD_NAMESPACE-$envValue.svc.cluster.local:8080/" \
 --from-literal=GRAD_TRAX_API="http://educ-grad-trax-api.$GRAD_NAMESPACE-$envValue.svc.cluster.local:8080/" \
 --from-literal=KEYCLOAK_TOKEN_URL="https://soam-$envValue.apps.silver.devops.gov.bc.ca/" \
 --from-literal=CONNECTION_TIMEOUT='60000' \
 --from-literal=MAXIMUM_POOL_SIZE='30' \
 --from-literal=MIN_IDLE='30' \
 --from-literal=IDLE_TIMEOUT='300000' \
 --from-literal=MAX_LIFETIME='600000' \
 --dry-run=client -o yaml | oc apply -f -
echo

echo Creating config map "$APP_NAME"-flb-sc-config-map
oc create -n "$GRAD_NAMESPACE"-"$envValue" configmap "$APP_NAME"-flb-sc-config-map \
  --from-literal=fluent-bit.conf="$FLB_CONFIG" \
  --from-literal=parsers.conf="$PARSER_CONFIG" \
  --dry-run=client -o yaml | oc apply -f -

SOAM_KC_LOAD_USER_ADMIN=$(oc -n $COMMON_NAMESPACE-$envValue -o json get secret sso-admin-${envValue} | sed -n 's/.*"username": "\(.*\)"/\1/p' | base64 --decode)
SOAM_KC_LOAD_USER_PASS=$(oc -n $COMMON_NAMESPACE-$envValue -o json get secret sso-admin-${envValue} | sed -n 's/.*"password": "\(.*\)",/\1/p' | base64 --decode)
SOAM_KC=soam-$envValue.apps.silver.devops.gov.bc.ca
SOAM_KC_REALM_ID="master"

echo Fetching SOAM token
TKN=$(curl -s \
  -d "client_id=admin-cli" \
  -d "username=$SOAM_KC_LOAD_USER_ADMIN" \
  -d "password=$SOAM_KC_LOAD_USER_PASS" \
  -d "grant_type=password" \
  "https://$SOAM_KC/auth/realms/$SOAM_KC_REALM_ID/protocol/openid-connect/token" | jq -r '.access_token')

echo Retrieving client ID for grad-graduation-report-api-service
CLIENT_UUID=$(curl -sX GET "https://$SOAM_KC/auth/admin/realms/$SOAM_KC_REALM_ID/clients" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TKN" |
  jq '.[] | select(.clientId=="'"$CLIENT_ID"'")' | jq -r '.id')

echo
echo Retrieving client secret for grad-graduation-report-api-service
SERVICE_CLIENT_SECRET=$(curl -sX GET "https://$SOAM_KC/auth/admin/realms/$SOAM_KC_REALM_ID/clients/$CLIENT_UUID/client-secret" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TKN" |
  jq -r '.value')

echo Creating secret for client
oc create -n "$GRAD_NAMESPACE"-"$envValue" secret generic $CLIENT_SECRET_NAME \
  --from-literal=GRAD_GRADUATION_REPORT_API_CLIENT_NAME="$CLIENT_ID" \
  --from-literal=GRAD_GRADUATION_REPORT_API_CLIENT_SECRET="$SERVICE_CLIENT_SECRET" \
  --dry-run=client -o yaml | oc apply -f -
