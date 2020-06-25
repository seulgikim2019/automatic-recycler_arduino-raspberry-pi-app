import socket
import RPi.GPIO as GPIO
import requests
import io
import time
import base64
from picamera import PiCamera

''' camera setting '''
camera = PiCamera()

''' motor setting '''
# motor 1
in1 = 19
in2 = 13
en = 26

# motor 2
in3 = 6
in4 = 5
en2 = 0

GPIO.setmode(GPIO.BCM)  # ?? need

# motor 1
GPIO.setup(in1, GPIO.OUT)
GPIO.setup(in2, GPIO.OUT)
GPIO.setup(en, GPIO.OUT)
GPIO.output(in1, GPIO.LOW)
GPIO.output(in2, GPIO.LOW)
p = GPIO.PWM(en, 1000)
p.start(25)

# motor 2
GPIO.setup(in3, GPIO.OUT)
GPIO.setup(in4, GPIO.OUT)
GPIO.setup(en2, GPIO.OUT)
GPIO.output(in3, GPIO.LOW)
GPIO.output(in4, GPIO.LOW)
p1 = GPIO.PWM(en2, 1000)
p1.start(25)


''' motor function '''
def motor1right():
    print('motor1right()')
    GPIO.output(in1, GPIO.HIGH)
    GPIO.output(in2, GPIO.LOW)
    
def motor1left():
    print('motor1left()')
    GPIO.output(in1, GPIO.LOW)
    GPIO.output(in2, GPIO.HIGH)
    
def motor1stop():
    print('motor1stop()')
    GPIO.output(in1, GPIO.LOW)
    GPIO.output(in2, GPIO.LOW)

def motor2right():
    print('motor2right()')
    GPIO.output(in3, GPIO.HIGH)
    GPIO.output(in4, GPIO.LOW)

def motor2left():
    print('motor2left()')
    GPIO.output(in3, GPIO.LOW)
    GPIO.output(in4, GPIO.HIGH)

def motor2stop():
    print('motor2stop()')
    GPIO.output(in3, GPIO.LOW)
    GPIO.output(in4, GPIO.LOW)

def non_metal_motor_run():
    print('non_metal_motor_run')
    motor1left()
    time.sleep(5)
    motor1stop()
    time.sleep(3)
    print('non_metal_motor_run_reverse')
    motor1right()
    time.sleep(5)
    motor1stop()
    print('non_metal_motor_stop')
    
def metal_motor_run():
    print('metal_motor_run')
    motor1right()
    time.sleep(5)
    motor1stop()
    time.sleep(3)
    print('metal_motor_run_reverse')
    motor1left()
    time.sleep(5)
    motor1stop()
    print('metal_motor_stop')

def plastic_motor2_run():
    print('plastic_motor2_run')
    motor2left()
    time.sleep(8)
    motor2stop()
    time.sleep(3)
    print('plastic_motor2_run_reverse')
    motor2right()
    time.sleep(8)
    motor2stop()
    print('plastic_motor2_stop')

def glass_motor2_run():
    print('glass_motor2_run')
    motor2right()
    time.sleep(8)
    motor2stop()
    time.sleep(3)
    print('glass_motor2_run_reverse')
    motor2left()
    time.sleep(8)
    motor2stop()
    print('glass_motor2_stop')

''' img analysis '''
imagePath = "/home/ksg/temp.png"

def postImg():
    print("Opening Camera")
    # camera = PiCamera()
    camera.start_preview()
    time.sleep(2)
    camera.capture(imagePath)
    camera.stop_preview()
    print("Image Captured!")

    imageFile = open(imagePath, "rb")
    imageBytes = base64.b64encode(imageFile.read())
    print("Sending image to cloud server for analysis.")
    #카메라 찍어서 응답받기(유리, 플라스틱 구분)
    response = requests.post("http://ip:port/detect", data=imageBytes)

    print("Response received!")
    response_data = response.json()
    print(response_data)

    # do parsing
    dict = response_data

    plastic = (dict['plastic'])
    glass = (dict['glass'])
    trash = (dict['trash'])
    metal = (dict['metal'])
    paper = (dict['paper'])
    cardboard = (dict['cardboard'])

    plastic = plastic - 0.1

    if plastic>glass:
        print("plastic")
        plastic_motor2_run()
    elif glass>plastic:
        print("glass")
        glass_motor2_run()


''' socket open! '''
IP = '192.168.0.001'
PORT = 5050
SIZE = 1024

def run_server(host=IP, port=PORT):
    print('wait...')
    sock = socket.socket()
    sock.bind((host, port))
    sock.listen(1)
    conn, addr = sock.accept()
    while True:
        data = conn.recv(SIZE)
        msg = data.decode()
        if msg == '0': # non_metal
            print('non_metal')
            non_metal_motor_run()
            postImg()
        elif msg == '1': # metal
            print('metal')
            metal_motor_run()


if __name__ == '__main__':
    run_server()