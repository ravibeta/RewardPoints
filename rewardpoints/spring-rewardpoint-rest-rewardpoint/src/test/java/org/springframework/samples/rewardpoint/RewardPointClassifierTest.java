package org.springframework.samples.rewardpoint;
import org.springframework.samples.rewardpoint.rewardpoint.RewardPointClassifier;
import org.springframework.samples.rewardpoint.rewardpoint.RewardPointRepository;
import org.springframework.samples.rewardpoint.rewardpoint.RewardPointPolicyRepository;
import org.springframework.samples.rewardpoint.model.Owner;
import org.springframework.samples.rewardpoint.model.RewardPoint;
import org.springframework.samples.rewardpoint.model.RewardPointPolicy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RewardPointClassifierTest {
    @Mock
    private RewardPointRepository pointRepo;
    @Mock
    private RewardPointPolicyRepository policyRepo;

    @Test
    public void testClassifier() {
        RewardPoint point = new RewardPoint();
        point.setDescription("description1");
        point.setPoints(5);
        point.setOwnerId(1);
        point.setDate(new Date());
        List<RewardPoint> points = new ArrayList<>();
        points.add(point);
        when(pointRepo.findByOwnerId(1)).thenReturn(points);
        RewardPointPolicy policy = new RewardPointPolicy();
        policy.setPredicate("descriptionLIKEdescription1");
        List<RewardPointPolicy> policies = new ArrayList<>();
        policies.add(policy);
        when(policyRepo.getPolicies()).thenReturn(policies);
        RewardPointClassifier classifier = new RewardPointClassifier(pointRepo, policyRepo);
        Assert.assertEquals(classifier.admit(point), true);
    }
}
