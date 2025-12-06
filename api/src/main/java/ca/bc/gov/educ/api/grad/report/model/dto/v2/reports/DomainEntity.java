package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import java.io.Serializable;
import java.util.Date;

public interface DomainEntity extends Serializable {

    /**
     * Retrieve the globally unique identity of the entity.
     *
     * @return A globally unique identifier for the entity.
     */
    String getEntityId();

    /**
     * Retrieve the timestamp on which this entity was first created.
     *
     * @return Timestamp of when the entity was created.
     */
    Date getCreatedOn();

    /**
     * Retrieve name of the user or system that created this entity.
     *
     * The value of the created by is the value of the User Principal object at
     * the time of creation.
     *
     * @return Name of the user which created this entity.
     */
    String getCreatedBy();

    /**
     * Retrieve the timestamp on which this entity was last updated.
     *
     * @return Timestamp of when this entity was last updated.
     */
    Date getLastUpdatedOn();

    /**
     * Retrieve name of the user or system that last updated this entity.
     *
     * The value of the created by is the value of the User Principal object at
     * the time of last update.
     *
     * @return User who last updated this entity.
     */
    String getLastUpdatedBy();

    /**
     * Retrieve the primary key id for persistence.
     *
     * @return id
     */
    Long getId();
}
