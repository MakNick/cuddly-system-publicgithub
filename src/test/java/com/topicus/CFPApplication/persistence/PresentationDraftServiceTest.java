package com.topicus.CFPApplication.persistence;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.topicus.CFPApplication.domain.PresentationDraft;

@RunWith(MockitoJUnitRunner.class)
public class PresentationDraftServiceTest {

	@Mock
	PresentationDraftRepository draftRepo;

	@InjectMocks
	PresentationDraftService draftService;

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
