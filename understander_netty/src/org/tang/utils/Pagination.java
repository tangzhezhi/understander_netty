package org.tang.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * 分页函数
 * 
 * @author allenpan
 */
public class Pagination<T> extends JdbcDaoSupport{
	
  public static final int NUMBERS_PER_PAGE = 10;
 //一页显示的记录数
  private int numPerPage; 
 //记录总数
  private int totalRows; 
 //总页数
  private int totalPages; 
 //当前页码
  private int currentPage; 
  //起始行数
  private int startIndex;
  //结束行数
  private int lastIndex;
  //结果集存放List
  private List<Map<String,Object>> resultList;
  //JdbcTemplate jTemplate
  
  private JdbcTemplate jTemplate;
 
  /**
   * 每页显示10条记录的构造函数,使用该函数必须先给Pagination设置currentPage，jTemplate初值
   * @param sql oracle语句
   */
  public Pagination(String sql,Object[] params ){
    if(jTemplate == null){
      throw new IllegalArgumentException("分页异常 ，jTemplate没有初始化");
    }else if(sql.equals("")){
      throw new IllegalArgumentException("分页异常 ，SQL没有初始化 ");
    }
    new Pagination(sql,params,currentPage,NUMBERS_PER_PAGE,jTemplate);
  }
  
  /**分页构造函数
   * @param sql 根据传入的sql语句得到一些基本分页信息
   * @param currentPage 当前页
   * @param numPerPage 每页记录数
   * @param jTemplate JdbcTemplate实例
   */
  public Pagination(String sql,Object[] params ,int currentPage,int numPerPage,JdbcTemplate jTemplate){
    if(jTemplate == null){
      throw new IllegalArgumentException("分页异常 ，jTemplate没有初始化. ");
    }else if(sql == null || sql.equals("")){
      throw new IllegalArgumentException("分页异常 ，SQL没有初始化 ");
    }
    //设置每页显示记录数
    setNumPerPage(numPerPage);
    //设置要显示的页数
    setCurrentPage(currentPage);
    //计算总记录数
    StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
    totalSQL.append(sql);
    totalSQL.append(" ) totalTable ");
    //给JdbcTemplate赋值
    setJdbcTemplate(jTemplate);
    //总记录数
    
    setTotalRows(getJdbcTemplate().queryForInt(totalSQL.toString(),params));
    //计算总页数
    setTotalPages();
    //计算起始行数
    setStartIndex();
    //计算结束行数
    setLastIndex();
    System.out.println("lastIndex="+lastIndex);//////////////////
    //构造oracle数据库的分页语句
    StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
    paginationSQL.append(" SELECT temp.*  FROM ( ");
    paginationSQL.append(sql);
    paginationSQL.append(" ) temp where ROWNUM <= " + lastIndex);
    paginationSQL.append(" ) WHERE ROWNUM > " + startIndex);
    //装入结果集
    setResultList(getJdbcTemplate().queryForList(paginationSQL.toString(),params));
  }
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }
 
  
  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getNumPerPage() {
    return numPerPage;
  }

  public void setNumPerPage(int numPerPage) {
    this.numPerPage = numPerPage;
  }

  public List<Map<String,Object>> getResultList() {
    return resultList;
  }

  public void setResultList(List<Map<String,Object>> resultList) {
	  this.resultList = resultList;
  }

  public int getTotalPages() {
    return totalPages;
  }
 //计算总页数
  public void setTotalPages() {
    if(totalRows % numPerPage == 0){
      this.totalPages = totalRows / numPerPage;
    }else{
      this.totalPages = (totalRows / numPerPage) + 1;
    }
  }

  public int getTotalRows() {
    return totalRows;
  }

  public void setTotalRows(int totalRows) {
    this.totalRows = totalRows;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public void setStartIndex() {
    this.startIndex = (currentPage - 1) * numPerPage;
  }

  public int getLastIndex() {
    return lastIndex;
  }

  public JdbcTemplate getJTemplate() {
    return jTemplate;
  }

  public void setJTemplate(JdbcTemplate template) {
    jTemplate = template;
  }
 //计算结束时候的索引
  public void setLastIndex() {
    System.out.println("totalRows="+totalRows);///////////
    System.out.println("numPerPage="+numPerPage);///////////
    if( totalRows < numPerPage){
      this.lastIndex = totalRows;
    }else if((totalRows % numPerPage == 0) || (totalRows % numPerPage != 0 && currentPage < totalPages)){
      this.lastIndex = currentPage * numPerPage;
    }else if(totalRows % numPerPage != 0 && currentPage == totalPages){//最后一页
      this.lastIndex = totalRows ;
    }
  }

}
