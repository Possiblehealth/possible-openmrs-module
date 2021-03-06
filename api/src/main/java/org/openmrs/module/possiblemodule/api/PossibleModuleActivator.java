/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 * <p>
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * <p>
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.possiblemodule.api;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleActivator;
import org.openmrs.module.bahmniemrapi.encountertransaction.service.BahmniEncounterTransactionService;
import org.openmrs.module.possiblemodule.api.advisor.PatientMergeAdvisor;
import org.openmrs.module.possiblemodule.api.advisor.PatientProgramAutoEnrolmentAdvisor;

/**
 * This class contains the logic that is run every time this module is either started or stopped.
 */
public class PossibleModuleActivator implements ModuleActivator {

    protected Log log = LogFactory.getLog(getClass());

    /**
     * @see ModuleActivator#willRefreshContext()
     */
    public void willRefreshContext() {
        log.info("Refreshing Program Auto Enrolment Module");
    }

    /**
     * @see ModuleActivator#contextRefreshed()
     */
    public void contextRefreshed() {
        log.info("Program Auto Enrolment Module refreshed");
    }

    /**
     * @see ModuleActivator#willStart()
     */
    public void willStart() {
        log.info("Starting Program Auto Enrolment Module");
    }

    /**
     * @see ModuleActivator#started()
     */
    public void started() {
        String globalProperty = Context.getAdministrationService().getGlobalProperty("possible.sub.modules","");
        String[] subModules = globalProperty.split("\\s*,\\s*");
        for (String subModule : subModules) {
            if("programAutoEnrollment".equals(subModule)){
                //TODO: if more subModules are added we shall have a start method on the sub module itself.
                Context.addAdvisor(BahmniEncounterTransactionService.class, new PatientProgramAutoEnrolmentAdvisor());
            }
            if("patientMergeValidation".equals(subModule)){
                Context.addAdvisor(PatientService.class, new PatientMergeAdvisor());
            }
        }
        log.info("Program Auto Enrolment Module started");
    }

    /**
     * @see ModuleActivator#willStop()
     */
    public void willStop() {
        log.info("Stopping Program Auto Enrolment Module");
    }

    /**
     * @see ModuleActivator#stopped()
     */
    public void stopped() {
        log.info("Program Auto Enrolment Module stopped");
    }

}
