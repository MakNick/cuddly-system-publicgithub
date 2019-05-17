package com.topicus.CFPApplication.persistence.api;

import com.topicus.CFPApplication.api.ApplicantEndpoint;
import com.topicus.CFPApplication.domain.Applicant;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.persistence.services.ApplicantService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(ApplicantEndpoint.class) // Zet dingen op zodat je gemakelijk endpoints kan testen, zonder de hele applicatie te moeten opstarten
public class ApplicantEndpointIT {

    @Autowired
    private MockMvc mvc;

    @MockBean // Omdat we alleen de endpoint willen testen, is het niet nodig om door de services te lopen. De services hebben hun eigen testen.
    private ApplicantService applicantService;

    @Test
    @WithMockUser(username = "admin", roles= {"ADMIN"})
    public void listApplicantsTest(){
        List<Applicant> applicantList = new ArrayList<>(Arrays.asList(new Applicant(), new Applicant(), new Applicant(), new Applicant()));

        applicantList.get(0).setName("eerste");
        applicantList.get(1).setName("tweede");
        applicantList.get(2).setName("derde");

        BDDMockito.given(this.applicantService.findAll()).willReturn(applicantList);

        try{
            mvc.perform(get("/api/applicants")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(4)))
                    .andExpect(jsonPath("$[0].name", is(applicantList.get(0).getName())))
                    .andExpect(jsonPath("$[1].name", is(applicantList.get(1).getName())))
                    .andExpect(jsonPath("$[2].name", is(applicantList.get(2).getName())));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    @WithMockUser(username = "admin", roles= {"ADMIN"})
    public void findApplicantByIdTest(){
        Applicant applicant = new Applicant();

        applicant.setName("first");

        BDDMockito.given(this.applicantService.findById(1L)).willReturn(Optional.of(applicant));

        try{
            mvc.perform(get("/api/applicants/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(applicant.getName())));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    @WithMockUser(username = "admin", roles= {"ADMIN"})
    public void showPresentationDraftsByApplicantTest(){

        PresentationDraft p1 = new PresentationDraft();
        Applicant applicant = new Applicant();

        p1.setSubject("this subject");
        applicant.setName("first");
        applicant.setPresentationDrafts(new HashSet<>(Arrays.asList(p1, new PresentationDraft(), new PresentationDraft(), new PresentationDraft())));

        BDDMockito.given(this.applicantService.showPresentationDraftsByApplicant(1L)).willReturn(Optional.of(applicant));

        try{
            mvc.perform(get("/api/applicants/1/presentationdrafts")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].subject", is(p1.getSubject())));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
