# Payment Service

 + [Introduction](#Introduction);
 + [Calculation of charges for viewing takes place](#Calculation);
 + [Manage to scale the solution](#Scale);
 + [Manage to deploy the associated configuration](#Configuration);
 + [Control this process](#Control);

## <a name="Introduction"></a> Introduction

Previously, we talked about the concept of billing that we have chosen at AdHive. [Billing](https://github.com/adhivetv/adhive.tv/tree/master/billing)

The Payment service became the first major "external service" in the AdHive Billing ecosystem.  
As the Payment service is a related one, it has inherited a number of key features inherent to classical billing.  
1. No ACID as all operations are atomic;
2. Lack of blocking of execution flows;
3. Scalable, linear micro-service;
4. Resistance to parallel launches;
5. Unified configuration space;
6. Launchable in docker container.
## <a name="Calculation"></a> Calculation of charges for viewing takes place

The diagram below demonstrates how daily calculation of charges for viewing takes place.

<img align="right" src="https://github.com/adhivetv/adhive.tv/blob/master/image/execute_payment.png" />

As a result, a payment order is issued for each blogger account. The order will be executed as part of payment orders fulfillment for the advertising company’s account.

As part of the internal mechanism for the execution of payment orders, all "verified orders" are transferred to an operation on the account (transaction), which, through the atomic mechanism of the storage layer, is guaranteed conversion into 2 Increment operations for the blogger account and a Decrement operation for the account of the advertising company.

We have now clarified how due payments are made daily.

## <a name="Scale"></a>  Manage to scale the solution 

How do we manage to scale the solution of conducting debiting operations?
1. The payment service architecture and the underlying instruments work atomically (without transactions).
2. The API of interaction between services (in terms of calculating payments) works asynchronously, as only messages receipt confirmation is expected.
3. When calculating the payment amount, we trust only statistics and billing account change history data, which allows us not to store the absolute value of the balance during the calculation procedure.

## <a name="Configuration"></a> Manage to deploy the associated configuration

How do we manage to deploy the associated configuration?

1. Our billing team loves the docker function.
2. We believe in micro-services and supply docker containers.
3. Configuration management can be implemented based on docker-compose and cloud-config.
4. Load balancing control takes place through the technological stack oss eureca / ribbon / feign

## <a name="Control"></a> Control this process

How do we control this process?

1. As already mentioned, the configuration is managed centrally in the git repository, and the latter is distributed by the cloud-config service.
2. Monitoring is based on jmx metrics (performance and availability).
3. Health check takes place during the deployment phase. In addition, there is set of tests for regular launch and for receiving health status.
