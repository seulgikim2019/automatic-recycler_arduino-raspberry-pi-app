import serial
import socket
import time

# 통신 정보 설정
HOST = '192.168.0.001'
PORT = 5050
SIZE = 1024

port="/dev/ttyUSB0"
serialFromArduino = serial.Serial(port,9600)
serialFromArduino.flushInput()
sock = socket.socket()
sock.connect((HOST,PORT))
while True:
	time.sleep(1)
	input_s = serialFromArduino.readline()
	input = int(input_s)
	print(input)

	# 0: 비금속
	if input==0:
		data = '0'
		sock.sendall(data.encode())

	# 1: 금속
	elif input==1:
		data = '1'
		sock.sendall(data.encode())