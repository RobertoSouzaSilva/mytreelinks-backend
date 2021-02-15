package com.robertosouza.mytreelinks.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3Client;

	@Value("${s3.bucket_link}")
	private String bucketName;

	public URL uploadImage(MultipartFile file) {
		try {

			String originalName = file.getOriginalFilename();

			InputStream inputStream = file.getInputStream();
			String contentType = file.getContentType();

			return uploadFile(inputStream, originalName, contentType);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	private URL uploadFile(InputStream inputStream, String fileName, String contentType) {
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentType(contentType);
		s3Client.putObject(bucketName, fileName, inputStream, meta);

		return s3Client.getUrl(bucketName, fileName);
	}
}
