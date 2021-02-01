package com.yzk.student.daoimp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yzk.student.dao.TeacherDao;
import com.yzk.student.model.Teacher;

/**
 * Created by hejjon on 2019/5/25.
 */
public class TeacherDaoImo implements TeacherDao {

	private final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/StudentSystem-JSP-Servlet?serverTimezone=UTC";
	private final String userName = "root";
	private final String password = "root";
	private ArrayList<Teacher> teachers;

	@Override
	public int insertTeacher(Teacher teacher) {
		int n = 0;
		String sql = "insert into Teacher values (default,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// 加载驱动
			Class.forName(jdbcDriver);
			// 获取连接
			conn = DriverManager.getConnection(url, userName, password);
			// 创建会话
			ps = conn.prepareStatement(sql);
			// 设置 ? 的值
			ps.setString(1, teacher.getName());
			ps.setString(2, teacher.getGender());
			ps.setInt(3, teacher.getAge());
			ps.setString(4, teacher.getClasss());
			// 执行sql
			n = ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 关闭数据库资源
			try {
				if (null != ps) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n;
	}

	@Override
	public int deleteTeacher(int id) {
		String sql = "delete from Teacher where id=?";
		int n = 0;
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			// 加载驱动
			Class.forName(jdbcDriver);
			// 获取连接
			conn = DriverManager.getConnection(url, userName, password);
			// 创建会话
			ps = conn.prepareStatement(sql);
			// 设置 ? 的值
			ps.setInt(1, id);
			// 执行sql
			n = ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != ps) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return n;
	}

	@Override
	public int updateTeacher(Teacher teacher) {
		String sql = "update Teacher set name=?, gender=?, age=?, classs=? where id=?";
		int n = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// 加载驱动
			Class.forName(jdbcDriver);
			// 获取连接
			conn = DriverManager.getConnection(url, userName, password);
			// 创建会话
			ps = conn.prepareStatement(sql);
			ps.setString(1, teacher.getName());
			ps.setString(2, teacher.getGender());
			ps.setInt(3, teacher.getAge());
			ps.setString(4, teacher.getClasss());
			ps.setInt(5, teacher.getId());
			n = ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != ps) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n;
	}

	@Override
	public List<Teacher> selectTeacherByName(String name) {
		teachers = new ArrayList<>();
		for (Teacher teacher : selectAllTeacher()) {
			if (teacher.getName().equals(name)) {
				teachers.add(teacher);
			}
		}
		return teachers;
	}

	@Override
	public List<Teacher> selectAllTeacher() {
		String sql = "select * from Teacher";
		List<Teacher> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// 加载驱动
			Class.forName(jdbcDriver);
			// 获取连接
			conn = DriverManager.getConnection(url, userName, password);
			// 创建会话
			ps = conn.prepareStatement(sql);
			// 获取结果集
			rs = ps.executeQuery();
			// 遍历结果集
			while (rs.next()) {
				Teacher teacher = new Teacher(rs.getInt("id"), rs.getString("name"), rs.getString("gender"),
						rs.getInt("age"), rs.getString("classs"));
				list.add(teacher);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != ps) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	@Override
	public Teacher selectTeacherById(int id) {
		for (Teacher teacher : selectAllTeacher()) {
			if (teacher.getId() == id) {
				return teacher;
			}
		}
		return null;
	}
}