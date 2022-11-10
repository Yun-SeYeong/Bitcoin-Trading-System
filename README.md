# Bitcoin-Trading-System

> Open API를 통해 Bitcoin 정보를 수집하고 분석하여 다양한 지표로 보여주고 나아가 자동으로 특정 포인트에 매수 또는 매도할 수 있도록한는 시스템이다. 다양한 전략을 구현할 수 있도록 유연한 설계를 목표로 한다.

## Overview

Upbit의 Open API를 이용하여 Candle정보를 수집하여 Database저장하고 이를 분석하여 보조 지표를 생성한다. 위 내용을 바탕으로 매매전략을 만들수 있고 Upbit Open API를 통해 자동으로 매매를 할 수 있다.

### 수집 과정

<img title="" src="file:///Users/yunseyeong/Documents/workspace/intellij/Bitcoin-Trading-System/docs/images/image1.png" alt="" style="cursor:pointer;max-width:100%;">

1. Upbit Service를 통해 Upbit의 데이터를 수집하여 Sync Service에서 DB에 저장한다.

2. 일봉, 분봉, 마켓 코드 등 베이스 데이터를 모두 수집하면 Sync Service에서 MACD, Slow Stochastic등 보조지표를 생성하고 저장한다.

### 수집

Upbit의 API를 이용해 일봉, 분봉, 마켓 코드를 수집한다.

- https://github.com/Yun-SeYeong/Bitcoin-Trading-System/issues/4
- https://github.com/Yun-SeYeong/Bitcoin-Trading-System/issues/5
- https://github.com/Yun-SeYeong/Bitcoin-Trading-System/issues/6

### 조회

수집된 일봉, 분봉, 마켓 코드를 조회한다.

- https://github.com/Yun-SeYeong/Bitcoin-Trading-System/issues/16
- https://github.com/Yun-SeYeong/Bitcoin-Trading-System/issues/2
- https://github.com/Yun-SeYeong/Bitcoin-Trading-System/issues/1

### 분석

Sync된 정보를 바탕으로 보조 지표를 만든다.

- https://github.com/Yun-SeYeong/Bitcoin-Trading-System/issues/15
- https://github.com/Yun-SeYeong/Bitcoin-Trading-System/issues/14

### 매매 시나리오

보조지표를 바탕으로 매수, 매도 신호를 감지할 시나리오를 만든다.

- https://github.com/Yun-SeYeong/Bitcoin-Trading-System/issues/27

### 매매 전략

매매 시나리오를 바탕으로 매입할 비중을 조절한다.
