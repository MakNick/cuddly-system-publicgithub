package com.topicus.CFPApplication.domain;

import com.topicus.CFPApplication.persistence.repositories.ApplicantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest // Dit geeft wat standaard setups die je nodig hebt om de persistence laag te testen
public class ApplicantTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Test
    public void FindByNameAndEmailTest() {

        // given
        Applicant applicant = new Applicant();

        applicant.setName("test");
        applicant.setName("test@test.nl");

        entityManager.persist(applicant);
        entityManager.flush();

        // when
        Optional<Applicant> found = applicantRepository.findApplicantByNameAndEmail(applicant.getName(), applicant.getEmail());

        // then
        assertThat(found.orElse(new Applicant()).getName()).isEqualTo(applicant.getName());
        assertThat(found.orElse(new Applicant()).getEmail()).isEqualTo(applicant.getEmail());
    }

}
