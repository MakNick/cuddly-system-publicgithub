package com.topicus.CFPApplication.persistence;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileService {

	public String saveDocumentInSaveDialog(String documentName) throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setSelectedFile(new File(documentName));
		fileChooser.setDialogTitle("Specify the location");
		int userSelection = fileChooser.showSaveDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			return fileToSave.getAbsolutePath();
		} else {
			throw new IOException("Save dialog was cancelled");
		}
	}
}