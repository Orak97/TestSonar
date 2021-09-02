package logic.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import logic.model.Cadence;
import logic.model.ContinuosActivity;
import logic.model.DAOActivity;
import logic.model.ExpiringActivity;
import logic.model.PeriodicActivity;
import logic.model.SuperActivity;

public class ManageActivityController {
	
	DAOActivity daoAc;
	
	/* Nel "Managing" di un'attivita potremmo volerne cambiare il nome, 
	 * pertanto si aggiunge un apposito metodo in cui si controlla
	 * se il nome nuovo e diverso da quello attualmente settato nell'attivita
	 * da noi passata.
	 */
	public SuperActivity setActivityName(SuperActivity sua,String name) {
		daoAc = DAOActivity.getInstance();
		if(!sua.getName().equals(name)) {
			sua.setName(name);
			daoAc.updateActivityJSON(sua);
		}
		return sua;
	}
	
	/* Nel "Managing" di un'attivita potremmo anche volerla cancellare.
	 * chiameremo il metodo di DAOActivity deleteActivityJSON cancellandola
	 * cosi dalla persistenza.
	 */
	public boolean deleteActivity(SuperActivity sua) {
		daoAc = DAOActivity.getInstance();
		if(!daoAc.deleteActivityJSON(sua))
			return false;
		return true;
	}

	/* Nel "Managing" di un'attivita potremmo anche voler cambiare la frequenza
	 * con cui l'attivita va a ripetersi. Si controlla prima che l'attivita non sia
	 * gia del tipo della nuova frequenza, fatto cio si aggiorna l'attivita anche
	 * nella persistenza.
	 */
	public SuperActivity setFrequency(SuperActivity sua, LocalTime opening, LocalTime closing ) {
		daoAc = DAOActivity.getInstance();
		if (!(sua.getFrequency() instanceof ContinuosActivity)) {
			sua.setFrequency(new ContinuosActivity(opening,closing));
			daoAc.updateActivityJSON(sua);
		}
		return sua;
	}
	
	public SuperActivity setFrequency(SuperActivity sua, LocalTime opening, LocalTime closing, LocalDate start, LocalDate end) {
		daoAc = DAOActivity.getInstance();
		if (!(sua.getFrequency() instanceof ExpiringActivity)) {
			sua.setFrequency(new ExpiringActivity(opening,closing,start,end));
			daoAc.updateActivityJSON(sua);
		}
		return sua;
	}
	
	public SuperActivity setFrequency(SuperActivity sua, LocalTime opening, LocalTime closing, LocalDate start, LocalDate end, Cadence cadence) {
		daoAc = DAOActivity.getInstance();
		if (!(sua.getFrequency() instanceof PeriodicActivity)) {
			sua.setFrequency(new PeriodicActivity(opening,closing,start,end,cadence));
			daoAc.updateActivityJSON(sua);
		}
		return sua;
	}
	
	
	
}
