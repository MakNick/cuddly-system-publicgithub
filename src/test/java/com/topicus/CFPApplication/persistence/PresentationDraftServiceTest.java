package com.topicus.CFPApplication.persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.naming.CannotProceedException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.topicus.CFPApplication.domain.PresentationDraft;
import com.topicus.CFPApplication.domain.PresentationDraft.Label;
import com.topicus.CFPApplication.domain.conference.Conference;

@RunWith(MockitoJUnitRunner.class)
public class PresentationDraftServiceTest {

	@Mock
	PresentationDraftRepository draftRepo;

	@InjectMocks
	PresentationDraftService draftService;

	@Test
	public void changeLabelTestLabelAlreadyExist() {
		PresentationDraft pres = new PresentationDraft();
		pres.setLabel(Label.ACCEPTED);
		Optional<PresentationDraft> opt = Optional.of(pres);

		Mockito.when(this.draftRepo.findById((long) 2)).thenReturn(opt);

		int result = this.draftService.changeLabel((long) 2, 2);

		Assert.assertEquals(0, result);
	}

	@Test
	public void changeLabelTestSuccesOrIDNotFound() {
		Optional<PresentationDraft> opt = Optional.of(new PresentationDraft());
		Optional<PresentationDraft> opt2 = Optional.ofNullable(null);

		Mockito.when(this.draftRepo.findById((long) 2)).thenReturn(opt);
		Mockito.when(this.draftRepo.findById((long) 3)).thenReturn(opt2);

		int result = this.draftService.changeLabel(2, 1);
		int result2 = this.draftService.changeLabel(3, 2);

		Assert.assertEquals(1, result);
		Assert.assertEquals(-1, result2);

	}

	@Test
	public void findByIdTest() {
		Optional<PresentationDraft> opt = Optional.of(new PresentationDraft());
		opt.get().setId(1);

		Mockito.when(this.draftRepo.findById((long) 1)).thenReturn(opt);

		PresentationDraft testItem = this.draftService.findById((long) 1).get();

		Mockito.verify(this.draftRepo).findById((long) 1);

		Assert.assertEquals(1, testItem.getId());

	}

	@Test
	public void deleteOkTest() {
		PresentationDraft pres = new PresentationDraft();
		Optional<PresentationDraft> opt = Optional.of(pres);
		Long id = 1L;

		Mockito.when(this.draftRepo.findById(id)).thenReturn(opt);

		Boolean result = this.draftService.delete(id);

		Mockito.verify(this.draftRepo).deleteById(id);

		Assert.assertEquals(true, result);

	}

	@Test
	public void deleteFailedTest() {
		Optional<PresentationDraft> opt = Optional.ofNullable(null);
		Long id = 1L;

		Mockito.when(this.draftRepo.findById(id)).thenReturn(opt);

		Boolean result = this.draftService.delete(id);

		Mockito.verify(this.draftRepo, Mockito.times(0)).deleteById(id);

		Assert.assertEquals(false, result);

	}
}
