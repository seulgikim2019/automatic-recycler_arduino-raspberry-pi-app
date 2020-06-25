#python2버전
#!/usr/bin/python
#*-* coding: utf-8*-*
import sys
from socket import *
import pymysql

s=socket(AF_INET,SOCK_STREAM)

s.bind(('',11000))

#여기서는 한명만 받기로 결정 -> 안에 1 숫자없애면 여러명.
s.listen(1)
print 'connect waiting.....'

conn, addr = s.accept()
mysql_conn = pymysql.connect('localhost','sql id','sql비밀번호','MyBin')
mysql_curs = mysql_conn.cursor()

print 'connected by',addr

while 1:
 data = str(conn.recv(1024)).decode("utf-8")
 item=data.split('/')
 print "data",data
 print "item",item[0]
 print "item1",item[1]
 sql1='SELECT gla_percent FROM glass order by gla_seq desc limit 1'
 sql2='SELECT can_percent FROM can order by can_seq desc limit 1'

 mysql_curs.execute(sql1)
 rows=mysql_curs.fetchall()
 

 print "plastic",str(rows)

 item1=str(item[0])
 itemdata="(("+item1+",),)"
 item2=str(item[1])
 item2data="(("+item2+",),)"
# print "datatest",datatest
 if str(rows) != itemdata:
   sql='INSERT INTO glass(gla_percent) VALUES (%s)'
   mysql_curs.execute(sql,(item1))
   mysql_conn.commit()
   print "dif1",item1

   mysql_curs.execute(sql2)
   rowss=mysql_curs.fetchall()
   if str(rowss) != item2data:
    sql='INSERT INTO can(can_percent) VALUES (%s)'
    mysql_curs.execute(sql,(item2))
    mysql_conn.commit()
    print "dif2",item2
   else:
    print "item2same"
 else:
   print "same1",item1
   mysql_curs.execute(sql2)
   rowss=mysql_curs.fetchall()
   if str(rowss) != item2data:
    sql='INSERT INTO can(can_percent) VALUES (%s)'
    mysql_curs.execute(sql,(item2))
    mysql_conn.commit()
    print "dif2",item2
   else:
    print "item2same"


conn.close()
s.close()

