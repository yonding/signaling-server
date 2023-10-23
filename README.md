# WebRTC로 구현하는 P2P 화상채팅
## signaling server란?
시그널링 서버는 두 피어 간의 연결을 위한 정보를 교환해주는 서버이다.<br><br>
## signaling server가 필요한 이유
WebRTC로 구현한 P2P 화상채팅에서는 미디어 데이터가 중앙서버를 거치지 않는다.<br>
하지만, 초기에 두 피어가 연결되기 위해서는 서로에 대한 정보를 교환해주어야 한다.<br>
두 피어의 연결 과정에서 이러한 정보 교환을 중개해주는 signaling server라고 한다.<br><br>


## 구성요소 간 상호작용
![image](https://github.com/yonding/signaling-server/assets/70754463/af16f731-1637-4c8b-9d20-b34cf3f26d6a)
