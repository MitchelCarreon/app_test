package com.example.app_test.Utils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScenarioViewModel extends ViewModel {
    MutableLiveData<Scenario> scenarioData;

    public ScenarioViewModel() {
        this.scenarioData = new MutableLiveData<>();
    }

    public MutableLiveData<Scenario> getScenarioData() {
        return scenarioData;
    }
    public void setCurrScenarioData(Scenario curr_scenario) {
        this.scenarioData.setValue(curr_scenario);
    }
}
