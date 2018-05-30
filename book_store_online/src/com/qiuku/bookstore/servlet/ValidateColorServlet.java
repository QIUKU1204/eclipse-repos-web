package com.qiuku.bookstore.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ValidateColorServlet
 */
@WebServlet("/validateColorServlet")
public class ValidateColorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CHECK_CODE_KEY = "CHECK_CODE_KEY";

	// 设置验证图片的宽度、高度及验证码的个数
	private int width = 152;
	private int height = 40;
	private int codeCount = 6;

	// 设置验证码字体的高度
	private int fontHeight = 4;

	// 验证码中的单个字符基线，即 验证码中的单个字符位于验证码图片左上角的(codeX, codeY)处
	private int codeX = 0;
	private int codeY = 0;

	// 验证码选用字符集
	char[] codeSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

	// 初始化验证码图片的基本属性
	public void init() {
		fontHeight = height - 2;
		codeX = width / (codeCount + 2);
		codeY = height - 4;
	}

	// 每次传入请求都会由service方法生成新的验证码图片
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 定义一个类型为 BufferedImage.TYPE_INT_BGR 的图像缓存
		BufferedImage buffImg = null;
		buffImg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

		// 在 buffImg 中创建一个 Graphics2D 图像
		Graphics2D graphics = null;
		graphics = buffImg.createGraphics();

		// 设置一个颜色
		graphics.setColor(Color.WHITE);

		// 填充一个指定的矩形: x - 矩形的x坐标; y - 矩形的y坐标; width - 矩形的宽度;
		// height - 矩形高度
		graphics.fillRect(0, 0, width, height);

		// 创建一个Font对象: name - 字体名称; style - Font的样式常量; size - Font 的点大小
		Font font = null;
		font = new Font("", Font.BOLD, fontHeight);
		// 设置 Graphics2D 图像使用此字体
		graphics.setFont(font);

		graphics.setColor(Color.BLACK);

		// 绘制指定矩形的边框
		graphics.drawRect(0, 0, width - 1, height - 1);

		// 随机产生15条干扰线, 使图片中的验证码不易被其他程序探测到
		Random random = null;
		random = new Random();
		graphics.setColor(Color.GREEN);
		for (int i = 0; i < 55; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(20);
			int y1 = random.nextInt(20);
			graphics.drawLine(x, y, x + x1, y + y1);
		}

		// 创建 randomCode 对象, 用于保存随机产生的验证码֤
		StringBuffer randomCode;
		randomCode = new StringBuffer();

		for (int i = 0; i < codeCount; i++) {
			// 
			String strRand = null;
			strRand = String.valueOf(codeSequence[random.nextInt(36)]);

			// 
			randomCode.append(strRand);

			// 
			graphics.setColor(Color.BLUE);
			graphics.drawString(strRand, (i + 1) * codeX, codeY);
		}

		// 将 randomCode 对象存入 Session 中，用于与输入字符作比较
		request.getSession().setAttribute(CHECK_CODE_KEY, randomCode.toString());

		// 
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 
		ServletOutputStream sos = null;
		sos = response.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}
	
}
