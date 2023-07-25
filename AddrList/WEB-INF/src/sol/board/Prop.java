package sol.board;

import java.sql.Connection;

public class Prop {
    static Connection conn;

    static final String listTemplate = """
        <meta charset='utf-8'>
        <style>
            table, th, td {
               border: 1px solid black;
               border-collapse: collapse;
            }
            th, td {
               padding: 5px;
            }
            a { text-decoration:none }
        </style>
        <center>
        <hr width='600' size='2' noshade>
        <h2>Simple Board with Servlet</h2>
        &nbsp;&nbsp;&nbsp;
        <a href='write'>글쓰기</a>
        &nbsp;&nbsp;&nbsp;
        <a href='../'>인덱스</a>
        <hr width='600' size='2' noshade>
        </center>
        <table border='1' width='600' align='center' cellpadding='2'>
        <tr>
        <th align='center' width='10%%'>글번호</th>
        <th align='center' width='15%%'>작성자</th>
        <th align='center' width='30%%'>이메일</th>
        <th align='center' width='30%%'>글제목</th>
        <th align='center' width='15%%'>날짜</th>
        </tr>
        %s
        </table>
        <hr width='600' size='2' noshade>
    """;

    static final String rowTemplate = """
        <tr>
        <td align='center'>%d</td>
        <td align='center'>%s</td>
        <td align='center'>%s</td>
        <td align='center'>
        <a href='content?seq=%d'>%s</a>
        </td>
        <td align='center'>%s</td>
        </tr>
    """;

    static final String contentTemplate = """
        <meta charset='utf-8'>
        <style>
            table, th, td {
               border: 1px solid black;
               border-collapse: collapse;
            }
            th, td {
               padding: 5px;
            }
            a { text-decoration:none }
        </style>
        <center>
        <hr width='600' size='2' noshade>
        <h2>Simple Board with Servlet</h2>
        &nbsp;&nbsp;&nbsp;
        <a href='write'>글쓰기</a>
        <hr width='600' size='2' noshade>
        <table border='1' width='600' align='center' cellpadding='3'>
        <tr>
        <td width='100' align='center'>글번호</td>
        <td>%s</td>
        </tr>
        <tr>
        <td align='center'>글쓴이</td>
        <td>%s</td>
        </tr>
        <tr>
        <td align='center'>이메일</td>
        <td>%s</td>
        </tr>
        <tr>
        <td align='center'>글제목</td>
        <td>%s</td>
        </tr>
        <tr>
        <td align='center'>글내용</td>
        <td>%s</td>
        </tr>
        </table>
        <hr width='600' size='2' noshade>
        <b>
        <a  href='write?seq=%s'>수정</a>
         | 
        <a href='update?type=del&seq=%s'>삭제</a>
         | 
        <a href='list'>목록</a>
        </b>
        <hr width='600' size='2' noshade>
        </center>
    """;

    static final String writeTemplate = """
        <html>
        <head>
          <title>간단한 게시판</title>
          <meta charset='utf-8'>
          <script language="javascript">
             function check()
             {
                 for(var i=0; i<document.input.elements.length; i++)
                 {
                    if(document.input.elements[i].value == "")
                    {
                       alert("모든 값을 입력 하셔야 합니다. ");
                       return false;
                    }
                 }
                 document.input.submit();
             }
          </script>
          <style>
              table, th, td {
                 border: 1px solid black;
                 border-collapse: collapse;
              }
              th, td {
                 padding: 5px;
              }
              a { text-decoration:none }
          </style>
        </head>
        <body onload="input.name.focus()">
          <center>
             <hr width="600" size="2" noshade>
                <h2>Simple Board with Servlet</h2>
                <a href='list'>글목록</a>
             <hr width="600" size="2" noshade>
          </center>
          <form name="input" method="post" action="update?type=%s">
             <table border="1" width="600" align="center"  cellpadding="3" cellspacing="1">
                <tr>
                   <td width="30%%" align="center">글쓴이</td>
                   <td align="center"><input type="text" name="writer" size="60" value="%s" %s></td>
                </tr>
                <tr>
                   <td align="center">이메일</td>
                   <td align="center"><input type="text" name="email" size="60" value="%s"></td>
                </tr>
                <tr>
                   <td align="center">글제목</td>
                   <td align="center"><input type="text" name="subject" size="60" value="%s"></td>
                </tr>
                <tr>
                   <td align="center">글내용</td>
                   <td align="center"><textarea name="content" rows="5" cols="53">%s</textarea></td>
                </tr>
                <tr>
                   <td colspan="2" align="center">
                      <input type="button" value="전송" onclick="check()">
                      <input type="reset" value="다시입력">
                   </td>
                </tr>
             </table>
             <br>
             <hr width="600" size="2" noshade>
          </form>
        </body>
      </html>
    """;
}
