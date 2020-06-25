<?php
/**
 * 사진 업로드
 */
 $img_upload = $_POST['img'];
 $temp_img_file_name = "temp1.png";
 $decoded_imgage = base64_decode("$imp_upload");
 $file_upload_result = file_put_contents($temp_img_file_name.".png",$decoded_imgage);
 if($file_upload_result == false){
     echo "사진 업로드 실패";
     exit;
 }else{
    echo "성공!!";
 }
?>
