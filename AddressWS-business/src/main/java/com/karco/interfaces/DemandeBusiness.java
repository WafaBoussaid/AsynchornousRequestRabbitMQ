package com.karco.interfaces;

import java.util.List;

import com.karco.entities.Demande;

public interface DemandeBusiness {
	public Demande mockSuggestions(Demande demande);
	public Demande validateSuggestion(Demande demande);
	public Demande getTripInfo(Demande demande);
}
