<?php

$connect = mysqli_connect("localhost", "root", "password","db table");
    
    
$query="select * from can order by can_seq desc limit 1";

$result=mysqli_query($connect,$query);


$url =array("trash" => "no");
while($row = mysqli_fetch_assoc($result)) {
 
   
    
    $query1="select * from plastic order by pla_seq desc limit 1";

    $result1=mysqli_query($connect,$query1);
    while($row1 = mysqli_fetch_assoc($result1)) {
    
        $query2="select * from glass order by gla_seq desc limit 1";

        $result2=mysqli_query($connect,$query2);

        while($row2 = mysqli_fetch_assoc($result2)) {
            $url = array("can_percent" => $row['can_percent']*1,"gla_percent" => $row2['gla_percent']*1,"pla_percent" => $row1['pla_percent']*1);

        }

    }



}

// header('Content-Type: application/json; charset=utf8');
// $json = json_encode(array($data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo json_encode($url);
?>