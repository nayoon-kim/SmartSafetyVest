단디, SmartSafetyVest
=====================

IoT 센서 기반의 스마트 해상물류 안전을 위한 스마트 안전조끼(Smart Safety Vest)
-----------------------------------
> __울산 항만에서는 조끼를 단디 입어라!__

> <img src="https://user-images.githubusercontent.com/53392870/99923171-de5e1d00-2d77-11eb-849e-40c92b943ef5.png" width="20%">  

## 프로그램 소개

스마트안전 조끼란, 항만 물류 작업자들의 재해율과 사망률을 줄이기 위하여 안전조끼 자체적으로 위험을 예방 및 처리하기 위한 작품이다. 
여러 센서와 아두이노 등을 안전조끼에 부착하는 HW적 기능요소와 모바일 프로그래밍을 사용하는 SW적 기능 요소가 결합된다. 
블루투스를 통해 측정한 GPS와 가스센스 센서값을 애플리케이션으로 전송하고, 문제를 처리하며 LTE/WIFI 통신을 통해 서버에 저장하고, 관리자 애플리케이션에전달하여 위치와 센서값을 통한 위험도를 관리자와 작업자 본인에게 가시적으로 나타내어 문제를 해결/예방하며 관리자 애플리케이션으로 작업자를 제어할 수 있게 설정한다. 
또한, 작업자가 신고를 통해 문제 상황을 알릴 수 있는 기능을 구현한다. 

## 프로그램 개발 배경

항만 물류 분야는 대부분의 시설이 20년 이상 노후화가 진행되고 있으며, 고착화 된 시설물 운영방식으로 신기술 도입 및 적용이 부족하다. 대형 컨테이너, 대형 화물 트럭, 유해가스, 석유 등 가연성 위험이 있는 물질 등에 노출된 작업 환경에 처해있으나, 위험요소들에 대한 안전사고 예방 대책이 미흡한 상황이다.
다양한 운영주체와 다양한 기술의 작업자, 관리자가 한 곳에 섞여 있어 관리가 제대로 이루어지지 않고 있는 상황이다.
기존 스마트 안전조끼와 관련한 기술은 작업자의 피해 예방에 초점을 맞추기보다는 작업자가 물리적 위험을 당했을 때 그 피해를 최소화 하는데 초점이 맞춰져 있었다.
안전 조끼에 장착된 센서를 통해 획득되는 사용자의 위치정보와 환경정보를 이용하여 위험도를 나타내어 피해최소화는 물론, 해양 물류 및 물류 작업자(사용자)의 위급상황을 예방하고, 관리자와 사용자가 어플리케이션을 사용하여 즉각적으로 문제 상황을 인지하고 공유, 관리하는 것을 목적으로 한다. 또한 어플리케이션의 신고버튼을 통해 문제를 즉각적으로 알릴 수 있다. 따라서 현존하는 제품들 보다 근본적 문제를 해결하고 예방까지 가능하도록 설계했다.

## 프로그램 특징

어플리케이션을 활용하여 기존의 추락방지 안전조끼나, 사용자의 생체정보를 획득하는 구명조끼처럼 문제가 생겼을 때 그 피해를 최소화 할 뿐만 아니라 관리자가 작업자를 집단적으로 관리, 위험을 예방하고, 작업자 역시 자신의 위험을 인식하고 즉각적인 대처를 하며, 관리자에게 알릴 수 있다.
해양물류 특성상 운영주체와 항만이 섞이고, 관리자와 작업자 역시 유동적으로 변화한다. 어플리케이션에서 운영주체와 항만 등을 선택하게 하여 관리자가 관리하는 작업자가 한정되고, 좀 더 작업자를 세분화하여 관리 할 수 있다.

## 프로그램 흐름도

> <img src="https://user-images.githubusercontent.com/53392870/99923187-eb7b0c00-2d77-11eb-9b68-1c6b5610c380.png" width="60%">

## 프로젝트 기능

> *좌측: 작업자 화면, 우측: 관리자 화면*
### 로그인 (Login)
***
관리자와 작업자로 분리하여 로그인하여 각각의 기능이 다르게 표시되도록 한다. 관리자의 경우 작업자들의 정보를 관리하기 때문에 서버에서 부여한 아이디와 비밀번호로 접속한다. 작업자의 경우 작업자가 관리자 별로 다르게 인식 될 수 있도록 부두, 항만, 소속 회사를 선택 할 수 있다.

> <img src="https://user-images.githubusercontent.com/53392870/99923195-f7ff6480-2d77-11eb-9280-93ea811832bb.png" width="40%">   
> <img src="https://user-images.githubusercontent.com/53392870/99923190-f2098380-2d77-11eb-8176-2d92e7f0c79f.png" width="40%">

### 작업자 위치 확인 기능
***
작업자 본인의 위치를 한 눈에 확인 할 수 있도록 지도에 나타낸다. 위험구역에 진입하면 스마트 안전 조끼에서 LED 점등과 알람을 통해 경고를 해준다. 관리자는 감독하는 작업자들의 작업 위치를 실시간으로 확인할 수 있다.

> <img src="https://user-images.githubusercontent.com/53392870/99923203-064d8080-2d78-11eb-8aef-563c33642316.png" width="40%">   
> <img src="https://user-images.githubusercontent.com/53392870/99923206-0b123480-2d78-11eb-947f-c5bc0e517b0c.png" width="40%">


### 작업자 위험도 확인 기능
***
스마트 안전 조끼로부터 작업자 주변 데이터를 실시간으로 수집하여 작업자 위험도를 이미지, 그래프, 텍스트로 표시한다. 수집하는 데이터는 온습도, 메탄가스, 일산화탄소, lpg 가스이다. 관리자는 감독하는 작업자들의 위험도를 확인 할 수 있다.

> <img src="https://user-images.githubusercontent.com/53392870/99923211-12d1d900-2d78-11eb-965f-f91af4891ece.png" width="40%">   
> <img src="https://user-images.githubusercontent.com/53392870/99923215-1b2a1400-2d78-11eb-9a11-1ec4a4aceaac.png" width="40%">

### 작업자 신고 기능
***
작업자 스스로 앱 메인 화면의 신고 접수 버튼을 누르거나 작업자 주변 데이터를 수집하여 작업자가 위험에 처했을 경우 신고를 접수 할 수 있다. 관리자는 작업자 신고 접수를 실시간으로 확인할 수 있는데, 알림으로 “작업자가 신고 접수하였습니다.” 가 가거나, “작업자 위험도 확인하기” 화면의 “안전도” 부분이 “신고 접수”로 표시된다.

> <img src="https://user-images.githubusercontent.com/53392870/99923223-20875e80-2d78-11eb-9f10-652a3fe7def7.png" width="40%">   
> <img src="https://user-images.githubusercontent.com/53392870/99923224-24b37c00-2d78-11eb-8e13-41b331eea70e.png" width="40%">

### 매뉴얼 기능
***
해상작업 시 알아야 할 안전 매뉴얼, 항만 작업자 안전 매뉴얼, 스마트 안전 조끼 사용법 매뉴얼 등의 정보를 제공한다. 

> <img src="https://user-images.githubusercontent.com/53392870/99923227-28470300-2d78-11eb-9b48-8929571d5776.png" width="30%">
> <img src="https://user-images.githubusercontent.com/53392870/99923232-2c732080-2d78-11eb-9a96-81af7fd1c3c7.png" width="30%">
> <img src="https://user-images.githubusercontent.com/53392870/99923238-309f3e00-2d78-11eb-9ce7-768144677b1f.png" width="30%">

### 작품 기대효과
***
스마트안전조끼를 도입하면 작업자 개인은 사각지대 및 중앙 관리 통제가 미흡 할 때에도 위험환경을 인지할 수 있고, 관리자는 스마트폰 프로그램을 사용하여 작업자의 위치를 정확히 파악하고, 유해물질 농도를 전달받아 작업자에게 알림 메시지를 보내는 등의 안전문제의 즉각적인 대응/관리를 할 수 있다.
작업자의 안전에 있어서 작업자 스스로가 책임져야할 개인의 안전을 앱이 일부분을 책임짐으로써 작업자가 마음 편히 업무를 진행할 수 있도록 한다.

### 작품 활용분야
***
항만 작업 흐름을 고려하여 제작되었기 때문에 스마트 안전조끼와 어플리케이션이 항만 작업에 쉽게 적용될 수 있다.
또한, 관리자는 관리 하에 있는 모든 작업자들의 상태를 확인할 수 있기 때문에 시야에 확보되지 않은 작업자까지 모두 관리할 수 있을 것이다.
항만 작업뿐만 아니라 안전조끼를 착용하는 작업 현장이라면 스마트 안전조끼를 착용할 수 있고 현장 관리자는 스마트 안전조끼를 착용한 작업자의 상태를 실시간으로 체크할 수 있기 때문에 다양한 작업 현장에서 활용할 수 있을 것이다.
현장 곳곳에서 일을 하고 있는 작업자의 주변 상태 데이터를 실시간으로 얻을 수 있기 때문에 작업자뿐만 아니라 작업 현장의 상태를 파악하여 현장 관리에 도움을 줄 수 있을 것이다.
### Youtube 주소
***
https://www.youtube.com/watch?v=v_z069HFVbw&list=PLqOnM6uAPZc6nGYPZmssIb3jJ3fJXrTr5&index=13

#### Android, STS, Tomcat 8.5, Arduino IDE

#### 2020 스마트 해상물류 경진대회


***

__Team__

* gyuminlee / https://github.com/gmlee329 (gmlee329@naver.com) *leader*
* nayoonkim / https://github.com/nayoon-kim (belloyv@gmail.com)
* hajeongkim / https://github.com/fell8000 (fell8000@naver.com)
