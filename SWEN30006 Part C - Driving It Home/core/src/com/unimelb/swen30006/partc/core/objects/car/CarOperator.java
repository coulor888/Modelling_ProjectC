package com.unimelb.swen30006.partc.core.objects.car;

import com.unimelb.swen30006.partc.core.objects.Car;

public class CarOperator {

	private enum turningStatus{
		left,
		straight,
		right	
	}
	private Car m_Car;
	private CarStatus m_CarStatus;
	private int speed;
	private boolean stopFlag = false;
	private turningStatus turning = turningStatus.straight;
	
	public CarOperator(Car car) {
		this.m_Car = car;
		m_CarStatus = CarStatus.breaking;
		speed = 0;
		turning = turningStatus.straight;
	}
	
	public void doSpeedUp(){
		m_CarStatus = CarStatus.accelerating;
	}
	
	public void doBreak(){
		m_CarStatus = CarStatus.breaking;
	}
	
	public void keepSpeed(int speed){
		m_CarStatus = CarStatus.keep;
		this.speed = speed;
	}
	
	public void keepSpeed(){
		m_CarStatus = CarStatus.keep;
		this.speed = (int) m_Car.getVelocity().len();
	}
	
	public void setStopFlag(){
		this.stopFlag = true;
		this.doBreak();
	}
	
	public void turningLeft(){
		turning = turningStatus.left;
	}
	
	public void turningRight(){
		turning = turningStatus.right;
	}
	
	public void update(float delta){
		
		switch (m_CarStatus) {
		case breaking:
			m_Car.brake();
			break;

		case accelerating:
			m_Car.accelerate();
			break;
		case keep:
			int currentSpeed = (int) m_Car.getVelocity().len();
			if (currentSpeed>this.speed) {
				m_Car.brake();
			} else {
				m_Car.accelerate();
			}
			break;
		}
		this.m_Car.update(delta);
		resetStopFlag();
	}
	
	private void resetStopFlag(){
		this.stopFlag = false;
	}
	
	private void resetTurning(){
		this.turning = turningStatus.straight;
	}
	
	
	
}
