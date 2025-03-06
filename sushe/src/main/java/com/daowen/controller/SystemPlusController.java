package com.daowen.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class SystemPlusController {


	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@RequestMapping("/admin/download")
	public void doDownload() {

		String filename = "";
		boolean state = true;

		String zyid = request.getParameter("zyid");
		String url = request.getParameter("url");

		Boolean isRemote = isUrl(url);
		if (url != null && !isRemote) {
			filename = url.substring(url.lastIndexOf("/") + 1);
			state = downLocalResource(filename);
		}
		if (url != null && isRemote) {
			state = downRemoteResource(url);
		}

	}

	private boolean downRemoteResource(String resurl) {


		boolean state=true;
		try {
			URL url = new URL(resurl);
			String filename = resurl.substring(resurl.lastIndexOf("/") + 1);
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(filename.getBytes("UTF-8"), "iso8859-1"));
			URLConnection connection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
			httpURLConnection.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 取得输入流，并使用Reader读取
			int responseCode = ((HttpURLConnection) connection).getResponseCode();
			BufferedInputStream bis = null;
			bis = new BufferedInputStream(httpURLConnection.getInputStream());
			ServletOutputStream sos = response.getOutputStream();

			int b;
			while ((b = bis.read()) != -1) {
				sos.write(b);
			}
			sos.close();
			bis.close();
		} catch (Exception e) {
            state=false;
		}
		return state;

	}

	private boolean downLocalResource(String filename) {
		BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;

		Boolean dlstate = true;
		try {

			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(filename.getBytes("UTF-8"), "iso8859-1"));

//			String resource= System.getProperty("user.dir")+"\\src\\main\\resources\\public\\";
//			String uploadDir = resource+ "upload\\temp\\";
			String uploadDir=request.getServletContext().getRealPath("/upload/temp/");
			bis = new BufferedInputStream(
					new java.io.FileInputStream(uploadDir+filename));

			bos = new java.io.BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dlstate = false;
		} finally {
			if (bis != null && bos != null)
				try {
					bis.close();
					bos.close();

				} catch (IOException e) {
					e.printStackTrace();
				}

		}
		return dlstate;
	}


	private boolean isUrl(String url) {
		Pattern pattern = Pattern
				.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
		return pattern.matcher(url).matches();
	}

	@RequestMapping("/admin/verificationcode")
	public void verificationCode(){
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}

		request.getSession().setAttribute("validcode", sRand);
		g.dispose();
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());

		    //out.clear();
		    //out =pageContext.pushBody();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}


}
