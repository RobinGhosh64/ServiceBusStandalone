// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.resourcemanager.servicebus.samples;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.core.management.Region;
import com.azure.resourcemanager.samples.Utils;
import com.azure.resourcemanager.servicebus.models.AuthorizationKeys;
import com.azure.resourcemanager.servicebus.models.KeyType;
import com.azure.resourcemanager.servicebus.models.NamespaceAuthorizationRule;
import com.azure.resourcemanager.servicebus.models.NamespaceSku;
import com.azure.resourcemanager.servicebus.models.Queue;
import com.azure.resourcemanager.servicebus.models.ServiceBusNamespace;

/**
 * Azure Service Bus basic scenario sample.
 * - Send a "Hello" message to queue using Data plan sdk for Service Bus.
 * - Delete queue
 * - Delete namespace
 */
public final class ServiceBusQueueBasic {

    /**
     * Main function which runs the actual sample.
     * @param azureResourceManager instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(AzureResourceManager azureResourceManager) {
        // New resources
        final String rgName = "my_rsg_1";
        final String namespaceName = "servicebusrsg01";
        final String queueName = "evt-consumer";
        final String connectionString = "Please add";
        try {
            

            //

            
            //=============================================================
            // Send a message to queue.
            ServiceBusSenderClient sender = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .queueName(queueName)
                .buildClient();
            sender.sendMessage(new ServiceBusMessage("Hello World"));
            System.out.println("Send message to the queue..");
            sender.close();
            System.out.println("Success..");

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
         finally {
            try {
                System.out.println("No Deleting Resource Group: " + rgName);
                
            } catch (NullPointerException npe) {
                System.out.println("Did not create any resources in Azure. No clean up is necessary");
            } catch (Exception g) {
                g.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Main entry point.
     *
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            final AzureProfile profile = new AzureProfile(AzureEnvironment.AZURE);
            final TokenCredential credential = new DefaultAzureCredentialBuilder()
                .authorityHost(profile.getEnvironment().getActiveDirectoryEndpoint())
                .build();

            AzureResourceManager azureResourceManager = AzureResourceManager
                .configure()
                .withLogLevel(HttpLogDetailLevel.BASIC)
                .authenticate(credential, profile)
                .withDefaultSubscription();

            // Print selected subscription
            System.out.println("Selected subscription: " + azureResourceManager.subscriptionId());

            runSample(azureResourceManager);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
