import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class DeviceFlowLimitedInput {

	public static void main(String args[])  {
		
		System.out.println("Example Device Flow Limited Input");
				
		try {
			Response response = requestDeviceCode();
			System.out.println(String.format("> status %d", response.code()));
			if(response.code() != 200) throw new Exception(response.body().string());
			CodeResponse CodeResponse = new Gson().fromJson(response.body().string(), CodeResponse.class);
			System.out.println(String.format("> Go to https://artik.cloud/go and enter code %s", CodeResponse.user_code));
			TokenResponse token = null;
			
			do {
				TimeUnit.SECONDS.sleep(5);
				System.out.println("Waiting to enter code, checking again in 5 secs ...");
				response = pollForCodeCompletion(CodeResponse);
				token = new Gson().fromJson(response.body().string(), TokenResponse.class);
			} while(response.code() != 200);
		
			System.out.println("\nSuccessful!  Code has been entered and received access token: " + token.access_token);
			System.out.println("\nYou may revoke your token by visiting https://my.artik.cloud/profile");
		    System.out.println("or by using https://accounts.artik.cloud/revokeToken API call");


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Response requestDeviceCode() throws IOException {
		
		System.out.println("Generating code ...");
		
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse(Config.CONTENT_TYPE);
		RequestBody body = RequestBody.create(mediaType, String.format("client_id=%s", Config.CLIENT_ID));
		Request request = new Request.Builder()
		  .url(Config.HOST_DEVICE_FLOW_CODE)
		  .addHeader("Content-Type", Config.CONTENT_TYPE)
		  .post(body)
		  .build();

		//{"device_code":"7efff7db-1cf1-4914-b5de-7a03e5f4ec5e","user_code":"WXNH-VRPJ","verification_url":"https://artik.cloud/go","expires_in":1800,"interval":5}
		return client.newCall(request).execute();
		
	}
	
	public static Response pollForCodeCompletion(CodeResponse response) throws IOException {
		
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse(Config.CONTENT_TYPE);
		RequestBody body = RequestBody.create(mediaType, String.format("client_id=%s&grant_type=device_code&code=%s", Config.CLIENT_ID, response.device_code));
		Request request = new Request.Builder()
		  .url(Config.HOST_DEVICE_FLOW_TOKEN)
		  .addHeader("Content-Type", Config.CONTENT_TYPE)
		  .post(body)
		  .build();
		
		return client.newCall(request).execute();
				
	}
		
}
