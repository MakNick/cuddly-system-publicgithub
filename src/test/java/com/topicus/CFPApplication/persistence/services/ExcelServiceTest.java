package com.topicus.CFPApplication.persistence.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.topicus.CFPApplication.domain.Conference;
import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.persistence.repositories.ConferenceRepository;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.topicus.CFPApplication.domain.Applicant;

@RunWith(MockitoJUnitRunner.class)
public class ExcelServiceTest {

    // Hier gaan de mocks in, vandaar de naam 'injectmocks'
    @InjectMocks
    ExcelService excelService;

    // Deze zit in de functie die getest wordt dus moet die worden gemockt
    @Mock
    ConferenceService conferenceService;

    // Hier moet nog even naar gekeken worden, niet qua test logica maar meer hoe je de tekst weer uit de 'workbook' haalt. Tot nu toe krijg ik het niet goed gedecodeerd.
    @Test
    public void createExcelTest() {

        // given
        Applicant applicantOne = new Applicant();
        applicantOne.setName("First applicant");
        Applicant applicantTwo = new Applicant();
        applicantOne.setName("Second applicant");

        PresentationDraft presentationDraft = new PresentationDraft();
        presentationDraft.setSubject(" ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,");
        presentationDraft.setApplicants(new HashSet<>(Arrays.asList(applicantOne, applicantTwo)));

        Conference conference = new Conference();
        conference.setName("Test conference");
        conference.setPresentationDrafts(new HashSet<>(Arrays.asList(presentationDraft, presentationDraft, presentationDraft, presentationDraft)));

        // when
        Mockito.when(this.conferenceService.findById(Mockito.anyLong())).thenReturn(Optional.of(conference));

        // then
        try {
            XSSFWorkbook workBook = this.excelService.createExcel(2L);
            Mockito.verify(this.conferenceService).findById(Mockito.anyLong());
            Assert.assertEquals("Name: /xl/workbook.xml - Content Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml", workBook.toString());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }


    }
}
