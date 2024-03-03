package photodownloader;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PhotoDownloader {

    public static void main(String[] args) {

	System.out.println(
		"put the external selenium jars (selenium java client jars & selenium server grid) on the classpath");
	System.out.println("these jars can be downloaded from here: https://www.selenium.dev/downloads/");

	ChromeDriver driver = new ChromeDriver();

	// navigate to the website
	driver.get("https://infinite-scroll.com/demo/full-page/page6");

	// Set the path to the ChromeDriver executable
	// my browser version: 122.0.6261.95
	// downloaded browser driver version: 122.0.6261.94
	// driver was downloaded from here:
	// https://storage.googleapis.com/chrome-for-testing-public/122.0.6261.94/win64/chrome-win64.zip
	System.setProperty("webdriver.chrome.driver",
		"D:\\Informatics\\eclipse workspaces\\2023 - 06\\photodownloader\\browserdriver\\chrome-win64");

	try {

	    // scrolling down
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    for (int i = 0; i < 5; i++) {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		Thread.sleep(2000); // Wait for the content to load
	    }

	    // find images
	    List<WebElement> imageElements = driver.findElements(By.tagName("img"));

	    // download images
	    for (int i = 0; i < imageElements.size(); i++) {
		WebElement imageElement = imageElements.get(i);
		String imageUrl = imageElement.getAttribute("src");
		downloadImage(imageUrl, "image_" + i + ".jpg");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    System.out.println("successfully executed");
	    // driver.quit();
	}
    }

    public static final void downloadImage(String imageUrl, String filename) {
	System.out.println("downloading...");
	System.out.println("imageUrl: " + imageUrl + " filename: " + filename);

	try {

	    URL url = new URL(imageUrl);
	    URLConnection connection = url.openConnection();
	    InputStream inputStream = connection.getInputStream();
	    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

	    // Path folderPath = Paths.get("D:/images");
	    Path filePath = Paths.get("D:/images/" + filename);
	    FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile());

	    byte[] buffer = new byte[1024];
	    int bytesRead;
	    while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
		fileOutputStream.write(buffer, 0, bytesRead);
	    }

	    fileOutputStream.close();
	    bufferedInputStream.close();
	    inputStream.close();

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    System.out.println("download complete");
	}
	return;
    }
}
