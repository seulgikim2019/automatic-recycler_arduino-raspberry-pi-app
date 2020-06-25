#python2버전에서 가능 
#!/usr/bin/python
#*-* coding: utf-8*-*
from socket import *
import RPi.GPIO as gpio
import time

#소켓연결
clientSock = socket(AF_INET, SOCK_STREAM)

print "connecting.."

#소켓 연결 ->IP,PORT
clientSock.connect(('IP', 11000))

print "connect okay"

#초음파센서 작동 (gpio.BOARD도 있음) -> 핀 셋팅
gpio.setmode(gpio.BCM)

#센서 핀 넘버 -> (trig,echo),(trig2,echo2)
trig = 20
echo = 19
trig2 = 21
echo2 = 26
  
#입출력 초기값 세팅
gpio.setup(trig, gpio.OUT)
gpio.setup(echo, gpio.IN)
gpio.setup(trig2, gpio.OUT)
gpio.setup(echo2, gpio.IN)
 
#함수 -> 초음파센서 거리측정
def dist(trig,echo):
 #return 변수
  global data

  gpio.output(trig, False)
  time.sleep(0.5)
  gpio.output(trig, True)
  time.sleep(0.0001)
  gpio.output(trig, False)

  while gpio.input(echo) == 0 :
    pulse_start = time.time()

  while gpio.input(echo) == 1 :
    pulse_end = time.time()

  #평균속도 340 -> 왔다갔다 왕복이므로 나누기 2 -> 17000
  pulse_duration = pulse_end - pulse_start
  distance = pulse_duration * 17000
  #오차가 좀 있어서 distance에서 1cm를 빼줌 -> 오차없을때 안빼주면 된다.
  distance = int(distance)-1
  
  #distance가 존재하는 경우
  if distance:
    print "distance",distance,"cm"
    #%차있는지 만들기 위해서 -> 전체 쓰레기통 길이를 63cm로 둠. 변경 가능
    data = int(distance)*100
    data = int(data)/63
  else:
    print "nodistance"  
  return data

#계속해서 값을 받아내야하므로 while문
try:
 while True:
  first_percent=dist(trig,echo)
  second_percent=dist(trig2,echo2)
  #소켓으로 값 전해주기 -> /로 구분시켜서 받을 것임.
  clientSock.send(str(first_percent)+"/"+str(second_percent))
except :
 #cleanup안에 핀값을 넣어 초기값셋팅. 안해줄시 추후에 이를 돌릴 때, 기억된 값부터 다시 체크가 되므로 초기값 해주기!
 gpio.cleanup(trig)
 gpio.cleanup(trig2)
 clientSock.close()
