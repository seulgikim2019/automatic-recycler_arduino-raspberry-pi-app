# automatic-recycler_arduino-raspberry-pi-app
금속, 유리, 플라스틱을 자동으로 분리해주는 쓰레기통.


기능
----
1. 1차 분류대에서 금속 센서를 이용해 금속과 비금속을 분리.
2. 2차 분류대에서 이미지(학습모델 이용)를 이용하여 
    플라스틱과 유리를 분리. 
3. [어플]금속(캔), 플라스틱, 유리 각 쓰레기통의 양을 보여주는 어플.




나의 구현기능
-------------
1. 2차 분류대에서 카메라 센서 제어하여 서버로 이미지 전송 기능 구현.
2. 초음파 센서를 연동하여 금속(캔), 플라스틱, 유리 각 쓰레기통 양이 얼마나 차 있는지 확인하는 어플 구현.
 


사용기술
--------
● 서버: GCP(Google Cloud Platform)

● 웹 서버: Flask

● 데이터베이스: Mysql

● 언어: Java, Python, C++

● 기타: 센서< 금속센서, 모터, 카메라센서, 초음파센서 >, 아두이노(Arduino), 라즈베리파이(Raspberry PI)


스크린샷
---------
<div>
<img src="https://user-images.githubusercontent.com/67361330/85678446-768da280-b703-11ea-9ce7-a4a5dc751f5f.png" width="100%"></img>
<img src="https://user-images.githubusercontent.com/67361330/85678507-86a58200-b703-11ea-94b4-daaf96596f56.png" width="100%"></img>
</div>


영상링크
--------
https://youtu.be/0fv9bcZLSr0

라이센스
--------
MIT License

Copyright (c) 2020 seulgikim2019

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
