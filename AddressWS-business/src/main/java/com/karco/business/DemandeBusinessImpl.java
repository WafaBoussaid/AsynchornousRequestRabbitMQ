package com.karco.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karco.dao.DemandeRepository;
import com.karco.entities.Demande;
import com.karco.interfaces.DemandeBusiness;
@Service
public class DemandeBusinessImpl implements DemandeBusiness {
	@Autowired
	private DemandeRepository demandeRepository; 
	
	@Override
	public Demande mockSuggestions(Demande demande) {
		
		Demande suggestion = demande;
		//calcul du score
		int score = 0;
		int scoreSuggestion = 0;
		
		
		switch(demande.getVehiculeType())
		{
			case"Citadine": score+=2; break;
			case"Berline":score+=1;break;
			case"Luxe":score+=0;break;
		}
		switch(demande.getMax_passenger())
		{
			case 0: score+=0; break;
			case 1:score+=1;break;
			case 2:score+=2;break;
			case 3:score+=3;break;
		}
		switch((int)demande.getMax_delay()/10)
		{
			case 0: score+=0; break;
			case 1:score+=1;break;
			case 2:score+=2;break;
			default:score+=3;break;
		}
		switch((int)demande.getMax_price()/10)
		{
			case 0: score+=0; break;
			case 1:score+=1;break;
			case 2:score+=2;break;
			default:score+=3;break;
		}
		// création d'une proposition
		switch(score)
		{
			case 1: scoreSuggestion = ThreadLocalRandom.current().nextInt(0, 10);break;
			case 2: scoreSuggestion = ThreadLocalRandom.current().nextInt(0, 10);break;
			case 3: scoreSuggestion = ThreadLocalRandom.current().nextInt(0, 10);break;
			
			case 4: scoreSuggestion = ThreadLocalRandom.current().nextInt(0, 20);break;
			case 5: scoreSuggestion = ThreadLocalRandom.current().nextInt(0, 20);break;
			case 6: scoreSuggestion = ThreadLocalRandom.current().nextInt(0, 20);break;
			
			case 7: scoreSuggestion = ThreadLocalRandom.current().nextInt(10, 50);break;
			case 8: scoreSuggestion = ThreadLocalRandom.current().nextInt(10, 50);break;
			case 9: scoreSuggestion = ThreadLocalRandom.current().nextInt(10, 50);break;
			
			case 10: scoreSuggestion = ThreadLocalRandom.current().nextInt(35, 100);break;
			case 11: scoreSuggestion = ThreadLocalRandom.current().nextInt(35, 100);break;
	   }
	   // pas de covoiturage possible
		if(scoreSuggestion <= 5)
			{
			suggestion.setMax_passenger(0);
			suggestion.setMax_delay(0);			
			suggestion.setMax_price(demande.getMax_price()*2.5);
			}
			
		if(scoreSuggestion > 5 && scoreSuggestion <= 8)
		{
			suggestion.setMax_passenger(0);
			suggestion.setMax_delay(0);			
			suggestion.setMax_price(demande.getMax_price()*1.5);
		}
		if(scoreSuggestion > 8 && scoreSuggestion <= 15)
		{
			suggestion.setMax_passenger(0);
			suggestion.setMax_delay(0);			
			suggestion.setMax_price(demande.getMax_price());
		}
		if(scoreSuggestion > 15 && scoreSuggestion <= 35)
		{
			suggestion.setMax_passenger(1);
			suggestion.setMax_delay(10);
			suggestion.setMax_price(demande.getMax_price()*0.80);
			
		}
		if(scoreSuggestion > 35 && scoreSuggestion <= 55)
		{
			suggestion.setMax_passenger(2);
			suggestion.setMax_delay(20);
			suggestion.setMax_price(demande.getMax_price()*0.65);
		}
		if(scoreSuggestion > 55)
		{
			suggestion.setMax_passenger(3);
			suggestion.setMax_delay(30);
			suggestion.setMax_price(demande.getMax_price()*0.5);
		}	
		suggestion.setVehiculeType(demande.getVehiculeType());
		return suggestion;
	}

	@Override
	public Demande validateSuggestion(Demande demande) {
		
		Demande suggestion = mockSuggestions(demande);
		// refus
		if( suggestion == null ||
			(suggestion.getMax_price() == demande.getMax_price() &&  suggestion.getMax_passenger() > demande.getMax_passenger())  || //// prix = , deviation =, passenger >
			(suggestion.getMax_price() == demande.getMax_price() && suggestion.getMax_passenger() == demande.getMax_passenger() && (suggestion.getMax_delay() - demande.getMax_delay())> 10 ) || //// prix =, passenger =, diviation > 10min 
			(suggestion.getMax_price() > (demande.getMax_price() * 1.15) && suggestion.getMax_delay() == demande.getMax_delay() && suggestion.getMax_passenger() == demande.getMax_passenger() ) || //// deviation =, passenger = , prix > 115%
			(suggestion.getMax_price() < demande.getMax_price() && suggestion.getMax_passenger() > (demande.getMax_passenger()+1) && suggestion.getMax_delay() == demande.getMax_delay() ) || /// deviation =, prix < et +1 passager
			(suggestion.getMax_passenger() == demande.getMax_passenger() && suggestion.getMax_delay() > (demande.getMax_delay()+10) && suggestion.getMax_price() > (demande.getMax_price()*0.75)) || // prix > 75% initial, passenger = , deviation > 10min
			(suggestion.getMax_passenger() < (demande.getMax_passenger()+1) && suggestion.getMax_price() > (demande.getMax_price()*1.15))  
		  )
	    {
		return null;
		}
		
		suggestion = getTripInfo(suggestion);
		
		return suggestion;
	}
	
	@Override
	public Demande getTripInfo(Demande demande) {
		try {

			URL url = new URL("https://router.project-osrm.org/route/v1/driving/" +
						      demande.getDep_lat() + "," +
						      demande.getDep_lon() + ";" +
						      demande.getArr_lat() + "," +
						      demande.getArr_lon() + "?overview=false" );

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				JSONObject jsonObject = new JSONObject(output);
				
				JSONArray routes = jsonObject.getJSONArray("routes");
				
				JSONObject jsonobject =  new JSONObject(routes.get(0).toString());
				
			    Integer D = ((int)jsonobject.getDouble("duration")/ 60);
			    Integer d = ((int)jsonobject.getDouble("distance")/ 1000);
			    demande.setDemandeDuration(D.toString());
			    demande.setDemandeDistance(d.toString());
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }

		return demande;
	}

}
