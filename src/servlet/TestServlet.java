package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Long currTime = null;
	SimpleDateFormat sdf = null;

	public void init() throws ServletException {// 初始化一些参数
		super.init();
		currTime = System.currentTimeMillis();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 在get中直接调用doPost方法
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {// 长连接方式
		boolean flag = true;// 用来表示长连接是否已经被断开（如果数据发送失败了就说明是断开了）
		while (true) {
			flag = this.sendData("jsFun", sdf.format(currTime), response);
			currTime = System.currentTimeMillis();
			if (!flag) {// 如果数据发送失败，那么就退出了，说明页面长连接已经断开了
				break;
			}
			try {// 每5秒发送一次
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @Title: sendData
	 * @Description: 用来定时向客户端返回数据
	 * @param jsFun要调用的js函数
	 * @param data要发送的数据
	 * @param response
	 */
	private boolean sendData(String jsFun, String data,
			HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			/* 这句话比较重要，我们通过response给页面返回一个js脚本，让js执行父页面的对应的jsFun，参数就是我们的data */
			response.getWriter().write(
					"<script type=\"text/javascript\">parent." + jsFun + "(\""
							+ data + "\")</script>");
			response.flushBuffer();
			return true;
		} catch (Exception e) {
			System.err.println("long connection was broken!");
			return false;
		}

	}

}
