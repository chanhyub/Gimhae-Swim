package com.alijas.gimhaeswim.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;


/**
 * 이미지 업로드를 위한 s3
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	public String bucket;  // S3 버킷 이름


	//test//////////////////////////

	public String testupload(MultipartFile multipartFile, String dirName, String contentsName,String maskname) throws IOException {

		File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
				.orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

		return testupload(uploadFile, dirName, contentsName,maskname);
	}

	private String testupload(File uploadFile, String dirName, String contentsName,String maskname) {
		String ext = (uploadFile.getName().substring(uploadFile.getName().lastIndexOf(".") + 1));//확장자뽑아냄
		String fileName = dirName + "/" + contentsName+"/" + maskname+"."+ext;   // S3에 저장된 파일 이름
		String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

		removeNewFile(uploadFile);

		return uploadImageUrl;
	}


	public String profileupload(byte[] profileimg, String dirName, String contentsName) throws IOException {

		File uploadFile = profileconvert(profileimg)  // 파일 변환할 수 없으면 에러
				.orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

		return upload(uploadFile, dirName, contentsName);
	}

	private Optional<File> profileconvert(byte[] file) throws IOException {

		File convertFile = new File(System.getProperty("user.dir") + "/" + "dump.jpg");
		if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
			try{
				FileOutputStream lFileOutputStream = new FileOutputStream(convertFile);
				lFileOutputStream.write(file);
				lFileOutputStream.close();
			}catch(Throwable e){
				e.printStackTrace(System.out);
			}

			return Optional.of(convertFile);
		}

		return Optional.empty();
	}
	//////////웹배너 저장되는곳
	public String bannerupload(MultipartFile multipartFile, String dirName,String imagename) throws IOException {

		File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
				.orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

		return bannerupload(uploadFile, dirName,imagename);
	}
	private String bannerupload(File uploadFile, String dirName,String imagename) {
		String ext = (uploadFile.getName().substring(uploadFile.getName().lastIndexOf(".") + 1));//확장자뽑아냄
		String fileName = dirName + "/"  + imagename+"."+ext;   // S3에 저장된 파일 이름
		String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

		removeNewFile(uploadFile);

		return uploadImageUrl;
	}
	///////////웹배너/////////

	public String testsaveImage(String imgUrl,String dirName, String contentsName, String maskname) throws IOException {
//      System.out.println("유알엘 들어온거"+imgUrl);
		HttpURLConnection conn = null;
		URL url = new URL(imgUrl);

		conn = (HttpURLConnection) url.openConnection();

		conn.setRequestProperty("Referer", imgUrl);

		String fileName = imgUrl.substring( imgUrl.lastIndexOf('/')+1, imgUrl.length() ); // 이미지 파일명 추출

		String ext = imgUrl.substring( imgUrl.lastIndexOf('.')+1, imgUrl.length() );  // 이미지 확장자 추출

		BufferedImage img = ImageIO.read(url);

		ImageIO.write(img, ext, new File(System.getProperty("user.dir") + "/" +fileName));
		File file = new File(System.getProperty("user.dir") + "/" +fileName);


		return testupload(file, dirName, contentsName, maskname);

	}

//	public String testthumbupload(MultipartFile multipartFile, String dirName, String contentsName,String maskname) throws IOException {
//
//		File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
//				.orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
//
//		return testupload(uploadFile, dirName, contentsName,maskname);
//	}
//
//	private String testthumbupload(File uploadFile, String dirName, String contentsName,String maskname) {
//		String ext = (uploadFile.getName().substring(uploadFile.getName().lastIndexOf(".") + 1));//확장자뽑아냄
//		String fileName = dirName + "/" + contentsName+"/" + maskname+"."+ext;   // S3에 저장된 파일 이름
//		String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
//
//		removeNewFile(uploadFile);
//
//		return uploadImageUrl;
//	}

	//test//////////////////////////

	/**
	 * 커뮤니티,사연 게시판에 올라가는 이미지
	 * @param multipartFile
	 * @param dirName
	 * @return
	 * @throws IOException
	 */
	public String boardimg(MultipartFile multipartFile, String dirName,String filename) throws IOException {

		File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
				.orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

		return boardupload(uploadFile, dirName,filename);
	}




	public String upload(MultipartFile multipartFile, String dirName, String contentsName) throws IOException {

		File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
				.orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

		return upload(uploadFile, dirName, contentsName);
	}

	public String uploadproduct(MultipartFile multipartFile, String dirName) throws IOException {

		File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
				.orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

		return upload(uploadFile, dirName);
	}

	// S3로 파일 업로드하기
	private String upload(File uploadFile, String dirName, String contentsName) {
		String randomsave = UUID.randomUUID().toString();//랜덤으로 바꿈
		String ext = (uploadFile.getName().substring(uploadFile.getName().lastIndexOf(".") + 1));//확장자뽑아냄
		String fileName = dirName + "/" + contentsName+"/" + randomsave+"."+ext;   // S3에 저장된 파일 이름
		String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

		removeNewFile(uploadFile);

		return uploadImageUrl;
	}

	// S3로 파일 업로드하기
	private String upload(File uploadFile, String dirName) {
		String randomsave = UUID.randomUUID().toString();//랜덤으로 바꿈
		String ext = (uploadFile.getName().substring(uploadFile.getName().lastIndexOf(".") + 1));//확장자뽑아냄
		String fileName = dirName + "/"  + randomsave+"."+ext;    // S3에 저장된 파일 이름
		String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

		removeNewFile(uploadFile);
		
		return uploadImageUrl;
	}
	// S3로 파일 업로드하기
	private String boardupload(File uploadFile, String dirName,String filename) {
//		String randomsave = UUID.randomUUID().toString();//랜덤으로 바꿈
		String ext = (uploadFile.getName().substring(uploadFile.getName().lastIndexOf(".") + 1));//확장자뽑아냄
		String fileName = dirName + "/"  + filename+"."+ext;    // S3에 저장된 파일 이름
		String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

		removeNewFile(uploadFile);

		return uploadImageUrl;
	}

	// S3로 업로드
	private String putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}

	// 로컬에 저장된 이미지 지우기
	private void removeNewFile(File targetFile) {
		if (targetFile.delete()) {
			log.info("File delete success");
			return;
		}
		log.info("File delete fail");
	}
	// 로컬에 파일 업로드 하기
	private Optional<File> convert(MultipartFile file) throws IOException {

		File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
		if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
			try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
				fos.write(file.getBytes());

			}

			return Optional.of(convertFile);
		}

		return Optional.empty();
	}
	//url로 로컬에 이미지 다운받기
	public String saveImage(String imgUrl,String dirName) throws IOException {
//      System.out.println("유알엘 들어온거"+imgUrl);
		URL url = new URL(imgUrl);

		String fileName = imgUrl.substring( imgUrl.lastIndexOf('/')+1, imgUrl.length() ); // 이미지 파일명 추출

		String ext = imgUrl.substring( imgUrl.lastIndexOf('.')+1, imgUrl.length() );  // 이미지 확장자 추출

		BufferedImage img = ImageIO.read(url);

		ImageIO.write(img, ext, new File(System.getProperty("user.dir") + "/" +fileName));
		File file = new File(System.getProperty("user.dir") + "/" +fileName);


		return upload(file, dirName);

	}

	public String saveDetailImage(String imgUrl,String dirName) throws IOException {//길이가 길어서 불러오지못하는 상세페이지 줄여서 새로그리는 함수

		int newWidth = 450;

		URL url = new URL(imgUrl);

		Image originalImage = ImageIO.read(url);

		int originWidth = originalImage.getWidth(null);
		int originHeight = originalImage.getHeight(null);

		// 기존 이미지 비율을 유지하여 세로 길이 설정
		int newHeight = (originHeight * newWidth)/originWidth;
		// 이미지 품질 설정
		// Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
		// Image.SCALE_FAST : 이미지 부드러움보다 속도 우선
		// Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
		// Image.SCALE_SMOOTH : 속도보다 이미지 부드러움을 우선
		// Image.SCALE_AREA_AVERAGING : 평균 알고리즘 사용
		Image resizeImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = newImage.getGraphics();
		graphics.drawImage(resizeImage, 0, 0, null);
		graphics.dispose();

		String fileName = imgUrl.substring( imgUrl.lastIndexOf('/')+1, imgUrl.length() ); // 이미지 파일명 추출

		String ext = imgUrl.substring( imgUrl.lastIndexOf('.')+1, imgUrl.length() );  // 이미지 확장자 추출



		ImageIO.write(newImage, ext, new File(System.getProperty("user.dir") + "/" +fileName));
		File file = new File(System.getProperty("user.dir") + "/" +fileName);


		return upload(file, dirName);

	}

	public void update(String key) {
		try {
			String[] url = key.split("/");
			int len = url.length;
			//   String name = url[len-1];
			String name = "";
			for(int i=3;i<len;i++)
			{
				if(i==3){
					name += url[i];
				}
				else{
					name += "/"+url[i];
				}

			}

			//Delete 객체 생성
			DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, name);

			//Delete
			amazonS3Client.deleteObject(deleteObjectRequest);

//			System.out.println(String.format("[%s] deletion complete", name));

		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
	}

}