package com.example.weather;

import java.text.SimpleDateFormat;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class WeatherClient extends WebServiceGatewaySupport {

	public GetCityForecastByZIPResponse getCityForecastByZip(String zipCode) {
		GetCityForecastByZIP request = new GetCityForecastByZIP();
		request.setZIP(zipCode);

		System.out.println();
		System.out.println("Requesting forecast for " + zipCode);

		GetCityForecastByZIPResponse response =
				(GetCityForecastByZIPResponse) getWebServiceTemplate()
						.marshalSendAndReceive(request, new SoapActionCallback(
								"http://ws.cdyne.com/WeatherWS/GetCityForecastByZIP"));

		return response;
	}

	public void printResponse(GetCityForecastByZIPResponse response) {
		ForecastReturn forecastReturn = response.getGetCityForecastByZIPResult();


		if (forecastReturn.isSuccess()) {
			System.out.println();
			System.out.println("Forecast for " + forecastReturn.getCity() + ", " +
					forecastReturn.getState());

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for (Forecast forecast : forecastReturn.getForecastResult().getForecast()) {
				System.out.print(format
						.format(forecast.getDate().toGregorianCalendar().getTime()));
				System.out.print(" ");
				System.out.print(forecast.getDesciption());
				System.out.print(" ");
				Temp temperature = forecast.getTemperatures();
				System.out.print(temperature.getMorningLow() + "\u00b0-" +
						temperature.getDaytimeHigh() + "\u00b0 ");
				System.out.println();
			}
		} else {
			System.out.println("No forecast received");
		}

	}

	public static void main(String[] args) {
		ApplicationContext context =
				new AnnotationConfigApplicationContext(WeatherConfiguration.class);
		WeatherClient client = context.getBean(WeatherClient.class);

		String zipCode = "94304";
		if (args.length > 0) {
			zipCode = args[0];
		}

		GetCityForecastByZIPResponse response = client.getCityForecastByZip(zipCode);
		client.printResponse(response);

	}

}
