import React, { useState, useEffect } from "react";
import { Button, Alert } from "reactstrap";
import { useAuth0 } from "@auth0/auth0-react";
import { getConfig } from "../config";

export const PricingComponent = () => {
  const { apiOrigin } = getConfig();

  const [products, setProducts] = useState([]);

  useEffect(() => {
    getProducts();
  }, []);

  const {
    getAccessTokenSilently,
    loginWithPopup,
    getAccessTokenWithPopup,
  } = useAuth0();

  const handleConsent = async () => {
    await getAccessTokenWithPopup();
  };

  const handleLoginAgain = async () => {
    await loginWithPopup();
  };

  const getProducts = async () => {
    const response = await fetch(`${apiOrigin}/api/getAllProducts`);
    setProducts(await response.json());
  };

  const subscribeProduct = async (productId) => {
    const response = await callApi('subscriptionCheckout', 'POST', productId);
    if (response.ok) {
      const stripeUrl = await response.text();
      window.location = stripeUrl;
    }
  };

  const callApi = async (endpoint, method = 'GET', requestBody = null) => {
    try {
      const token = await getAccessTokenSilently();
      return fetch(`${apiOrigin}/api/${endpoint}`, {
        method,
        headers: {
          Authorization: `Bearer ${token}`,
        },
        body: requestBody,
      });
    } catch (error) {
      if (error.error === 'login_required') {
        handleLoginAgain();
      } else if (error.error === 'consent_required') {
        handleConsent();
      }
      return { ok: false };
    }
  };

  return (
    <div id="generic_price_table">
      <section>
        <div className="container">
          <div className="row">
            {products.map(product => (
              <div key={product.productId} className="col-md-4">
                <div className="generic_content clearfix">
                  <div className="generic_head_price clearfix">
                    <div className="generic_head_content clearfix">
                      <div className="head_bg"></div>
                      <div className="head">
                        <span>{product.name}</span>
                      </div>
                    </div>
                  </div>
                  <div className="generic_feature_list">
                    <ul>
                      <li>{product.description}</li>
                    </ul>
                    {product.images.map(src => (<img key={src} src={src} alt={product.name}></img>))}
                  </div>
                  {product.prices.map(price => (
                    <Button
                      color="primary"
                      key={price.priceId}
                      onClick={() => subscribeProduct(price.priceId)}>
                      Subscribe ({price.unitAmount} {price.currency})
                    </Button>
                  ))}
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
};

export default PricingComponent;
