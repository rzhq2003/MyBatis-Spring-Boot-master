package tk.mybatis.springboot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.springboot.response.ResObject;

import java.io.IOException;

/**
 * 异常拦截处理器
 *
 * @author chenmc
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	@SuppressWarnings("unused")
	private static final String logExceptionFormat = "Capture Exception By GlobalExceptionHandler: Code: %s Detail: %s";
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// 运行时异常
	@ExceptionHandler(RuntimeException.class)
	public ResObject runtimeExceptionHandler(RuntimeException ex) {
		return resultFormat(1, ex);
	}

	// 空指针异常
	@ExceptionHandler(NullPointerException.class)
	public ResObject nullPointerExceptionHandler(NullPointerException ex) {
		return resultFormat(2, ex);
	}

	// 类型转换异常
	@ExceptionHandler(ClassCastException.class)
	public ResObject classCastExceptionHandler(ClassCastException ex) {
		return resultFormat(3, ex);
	}

	// IO异常
	@ExceptionHandler(IOException.class)
	public ResObject iOExceptionHandler(IOException ex) {
		return resultFormat(4, ex);
	}

	// 未知方法异常
	@ExceptionHandler(NoSuchMethodException.class)
	public ResObject noSuchMethodExceptionHandler(NoSuchMethodException ex) {
		return resultFormat(5, ex);
	}

	// 数组越界异常
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResObject indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
		return resultFormat(6, ex);
	}

	// 400错误
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public ResObject requestNotReadable(HttpMessageNotReadableException ex) {
		System.out.println("400..requestNotReadable");
		return resultFormat(7, ex);
	}

	// 400错误
	@ExceptionHandler({ TypeMismatchException.class })
	public ResObject requestTypeMismatch(TypeMismatchException ex) {
		System.out.println("400..TypeMismatchException");
		return resultFormat(8, ex);
	}

	// 400错误
	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public ResObject requestMissingServletRequest(MissingServletRequestParameterException ex) {
		System.out.println("400..MissingServletRequest");
		return resultFormat(9, ex);
	}

	// 405错误
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public ResObject request405(HttpRequestMethodNotSupportedException ex) {
		return resultFormat(10, ex);
	}

	// 406错误
	@ExceptionHandler({ HttpMediaTypeNotAcceptableException.class })
	public ResObject request406(HttpMediaTypeNotAcceptableException ex) {
		System.out.println("406...");
		return resultFormat(11, ex);
	}

	// 500错误
	@ExceptionHandler({ ConversionNotSupportedException.class, HttpMessageNotWritableException.class })
	public ResObject server500(RuntimeException ex) {
		System.out.println("500...");
		return resultFormat(12, ex);
	}

	// 栈溢出
	@ExceptionHandler({ StackOverflowError.class })
	public ResObject requestStackOverflow(StackOverflowError ex) {
		return resultFormat(13, ex);
	}

	// 其他错误
	@ExceptionHandler({ Exception.class })
	public ResObject exception(Exception ex) {
		return resultFormat(14, ex);
	}

	private <T extends Throwable> ResObject resultFormat(Integer code, T ex) {
		ex.printStackTrace();
		return ResObject.build(code, ex.getMessage());
	}

}
