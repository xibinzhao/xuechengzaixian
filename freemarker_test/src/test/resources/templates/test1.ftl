<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <title>Hello World!</title>
</head>
<body>
<#--Hello ${name}!-->
Hello ${name}
<br/>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>金额</td>
        <td>生日</td>
    </tr>
    <#list students as stu>
        <tr>
            <td>${stu_index}</td>
            <td>${stu.name}</td>
            <td>${stu.age}</td>
            <td>${stu.money}</td>
            <td>${stu.birthday?date}</td>
        </tr>
    </#list>
    <tr>
        <td>${stuMap.stu1.name}</td>
        <td>${stuMap.stu1.age}</td>
        <td>${stuMap["stu1"].money}</td>
        <td>${stuMap["stu1"].birthday?date}</td>
    </tr>
    <tr>
        <#list stuMap?keys as k>
            <td>${stuMap[k].name}</td>
            <td>${stuMap[k].age}</td>
            <td>${stuMap[k].money}</td>
            <td>${stuMap[k].birthday?date}</td>
            <br/>
        </#list>
    </tr>
    <tr <#if (students?size >1 )>style="background-color: red" </#if>>
        <td>11</td>
    </tr>
    <tr <#if !(stuMap.stu1.friends?? )>style="background-color: blue" </#if>>
        <td>22</td>
    </tr>
</table>

</body>
</html>
