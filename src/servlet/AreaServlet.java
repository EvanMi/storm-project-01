package servlet;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import vo.AreaVo;
import myhbase.dao.HbaseDao;
import myhbase.dao.HbaseDaoImpl;

public class AreaServlet extends HttpServlet {

	private static final long serialVersionUID = 2142035146439166837L;

	HbaseDao dao;

	String today;

	String historyday;

	String historydata;

	SimpleDateFormat sdf;

	public void init() throws ServletException {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		dao = new HbaseDaoImpl();
		today = sdf.format(new Date());
		historyday = getLastWeeksDay(today);

		historydata = getData(historyday, dao);
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		while (true) {

			String dateStr = sdf.format(new Date());

			if (!StringUtils.equals(dateStr, today)) {
				// 跨天处理
				today = dateStr;
				historyday = getLastWeeksDay(today);
				historydata = getData(historyday, dao);
			}

			// 每隔3秒查询一次
			String data = this.getData(today, dao);
			String jsonData = "{\'todayData\':" + data + ",'hisData':"
					+ historydata + "}";

			boolean flag = this.sentData("jsFun", jsonData, response);
			if (!flag) {
				break;
			}
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public String getLastWeeksDay(String today) {

		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new Date(sdf.parse(today).getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.set(Calendar.WEEK_OF_MONTH, cal.get(Calendar.WEEK_OF_MONTH) - 1);

		return sdf.format(cal.getTime());
	}

	public boolean sentData(String jsFun, String data,
			HttpServletResponse response) {

		try {
			response.setContentType("text/html;charset=utf-8");
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

	public String getData(String date, HbaseDao dao) {

		List<Result> rows = dao.getRows("area_order", date, "cf", "order_amt");

		AreaVo areaVo = new AreaVo();
		String areaid = "";
		for (Result rs : rows) {
			for (Cell cell : rs.listCells()) {
				String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
				String value = new DecimalFormat("#.0").format(Bytes
						.toDouble(CellUtil.cloneValue(cell)));
				String qualifier = Bytes
						.toString(CellUtil.cloneQualifier(cell));

				if (StringUtils.equals(qualifier, "order_amt")
						&& StringUtils.split(rowkey, "_").length == 2) {
					areaid = StringUtils.split(rowkey, "_")[1];
					areaVo.setData(areaid, value);
				}
			}
		}

		String result = "[" + areaVo.getBeijing() + "," + areaVo.getShanghai()
				+ "," + areaVo.getTaiyuan() + "," + areaVo.getTianjin() + ","
				+ areaVo.getFanshi() + "]";

		return result;
	}

}
