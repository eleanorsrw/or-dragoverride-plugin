//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.waterloorocketry.dragoverride;

import com.waterloorocketry.dragoverride.simulated.AeroData;
import com.waterloorocketry.dragoverride.simulated.ReadCSV;
import com.waterloorocketry.dragoverride.util.LazyMap;
import info.openrocket.core.simulation.SimulationConditions;
import info.openrocket.core.simulation.exception.SimulationException;
import info.openrocket.core.simulation.extension.AbstractSimulationExtension;


public class DragOverride extends AbstractSimulationExtension {
    public DragOverride() {
    }

    public void initialize(SimulationConditions conditions) throws SimulationException {
        conditions.getSimulationListenerList().add(new DragOverrideSimulationListener());

        ReadCSV readCSV = new ReadCSV();
        LazyMap<Double, AeroData, AeroData> dragData = readCSV.readCSV(this.getCSVFile());
//        System.out.println(dragData.get(0.1).toString());

        int i = 0;
        double cd1 = 0;
        double cd2 = 0;
        double mach1 = 0;
        double mach2 = 0;
        double interpolatedcd = 0;

        for (Double key : dragData.keySet()) {
            AeroData aeroData = dragData.get(key);
//            System.out.println(key + " " + aeroData.getCd());

            mach1 = key;
            cd1 = aeroData.getCd();

            if (i > 0) {
                System.out.println("cd1 is: " + cd1);
                System.out.println("cd2 is: " + cd2);
                System.out.println("mach1 is: " + mach1);
                System.out.println("mach2 is: " + mach2);

                interpolatedcd = cd1 + ((cd2-cd1)/0.01) * (mach2-mach1);
                System.out.println("interpolatedcd is: " + interpolatedcd);
            }

            mach2 = mach1;
            cd2 = cd1;

            i++;
        }
    }

    public String getName() {
        return "DragOverride";
    }

    @Override
    public String getDescription() {
        return "A plugin that overrides OpenRocket's drag coefficient based on a selected csv file.";
    }

    public String getCSVFile() {
        return config.getString("csvFile", "");
    }

    void setCSVFile(String csvFile) {
        config.put("csvFile", csvFile);
        fireChangeEvent();
    }
}
