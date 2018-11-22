package com.cloud99.invest.util;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class FileUtil {

	public String getFileContents(String filePath) throws Exception {

		URL resource = FileUtil.class.getResource(filePath);
		if (resource == null) {
			throw new FileNotFoundException("File not found: " + filePath);
		}
		try (InputStream inputStream = resource.openStream()) {
			return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
		}
	}
}
