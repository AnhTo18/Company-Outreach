package org.springframework.samples.outreach.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.stereotype.Repository;

/**
 * Repository class for <code>Subscription</code> domain objects 
 * @author creimers
 * @author kschrock
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>  {

}
