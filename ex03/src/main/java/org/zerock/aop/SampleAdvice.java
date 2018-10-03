package org.zerock.aop;

import java.sql.SQLException;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //스프링의 빈으로 인식되게 하기 위해
@Aspect //aop기능을 하는 클래스에 선언
public class SampleAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	private static String type="";
	private static String name="";
	
	//실행시 전달되는 파라미터 정보 파악하기.
	/*
	@Before("execution(* org.zerock.service.MessageService*.*(..))")
	public void startLog(JoinPoint jp) {
		logger.info("========================================");
		logger.info("========================================");
		
		logger.info("전달되는 파라미터 : " + Arrays.toString(jp.getArgs()) );
		logger.info("해당 Advice Type : " + jp.getKind());
		logger.info("실행하는 대상객체의 메소드정보 : " + jp.getSignature());
		logger.info("Target객체 : " + jp.getTarget());
		logger.info("Advice를 행하는 객체 : " + jp.getThis());
	}*/
	
	/**
	 * 가장 강력한  Around Advice
	 *  - 다른 타입과 다르게 메소드 실행에 직접 관여
	 *  - 파라미터로 ProceedingJoinPoint 타입을 사용할 수 있음. 
	 * 
	 * ProceedingJoinPoint
	 *  - 다음 Advice를 실행하거나, 실제 target 객체의 메소드를 실행하는 기능.
	 *  - proceed()메소드는 Exception보다 상위의 Throwable을 처리해야한다. 
	 */
	
	@Around("execution(* org.zerock.service.MessageService*.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable{
		logger.info("=========================================");
		long startTime = System.currentTimeMillis();
		logger.info("전달되는 파라미터 : " + Arrays.toString(pjp.getArgs()));
		
		type = pjp.getSignature().getDeclaringTypeName();
		
		if(type.indexOf("Controller") > -1) {
			name = "Controller \t : ";
			
		}else if(type.indexOf("Service")>-1) {
			name = "ServiceImpl \t : ";
		}else if(type.indexOf("DAO") >-1) {
			name="DAO \t\t : ";
		}
		logger.info("timeLog Aspect type : " + type);
		logger.info("timeLog Aspect name : " + name);
		
		Object result = pjp.proceed();
		
		long endTime = System.currentTimeMillis();
		
		logger.info("실행되는 대상객체의 메소드 정보 : " + pjp.getSignature().getName()+ " : " + (endTime-startTime));
		logger.info("=========================================");
		
		return result;
	}
	
	//복수의 예외처리
	/*SQLException 발생시 someTrouble, anyTrouble, trouble모두 동작
	 *  advice로 잡을려는 예외를 상속한 예외는 모두 잡아서 동작.
	 */
	@AfterThrowing(value="execution(* *..*Service.*(..))", throwing="ex")
	public void someTrouble(Throwable ex) {
		//메소드 호출이 예외를 던졌을때 발생하는 advice 
		System.out.println("*** Throwable!");
		System.out.println(ex.getMessage());
	}
	
	@AfterThrowing(value="execution(* *..*Service.*(..))", throwing="ex")
	public void anyTrouble(SQLException ex) {
		//메소드 호출이 예외를 던졌을때 발생하는 advice 
		System.out.println("*** SQLException");
		System.out.println(ex.getMessage());
	}
	
	@AfterThrowing(value="execution(* *..*Service.*(..))", throwing="ex")
	public void trouble(JoinPoint jp, Exception ex) {
		//메소드 호출이 예외를 던졌을때 발생하는 advice 
		System.out.println("*** Exception");
		System.out.println("method : " + jp.toShortString());
		System.out.println(ex.toString());
	}	
}
