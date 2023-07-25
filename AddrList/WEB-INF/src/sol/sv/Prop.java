package sol.sv;

import java.sql.Connection;

public class Prop {
    public static Connection conn;
    
    public static final String htmlTemplate = """
        <meta charset="utf-8">
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
            <h1>
                Address List 
            </h1>
            <h2>
                <a href="input.html">입력</a>
            </h2>
            <table border='1' cellpadding="7" cellspacing="2" width="50%%">
                <tr>
                    <th>번호</th>
                    <th>이름</th>
                    <th>주소</th>
                    <th>날짜</th>
                    <th>삭제</th>
                </tr>
                %s
            </table>
        </center>
        """;

    public static final String rowTemplate = """
        <tr>
            <td align='center'>%d</td>
            <td>%s</td>
            <td>%s</td>
            <td>%s</td>
            <td align='center'><a href='del?seq=%d'>삭제</a></td>
        </tr>
        """;
}
