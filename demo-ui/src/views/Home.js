import React, { Fragment } from "react";

import Hero from "../components/Hero";
import ExternalApi from "../components/ExternalApi";
import Pricing from "../components/Pricing";

const Home = () => (
  <Fragment>
    <Hero />
    <hr />
    <ExternalApi skipToken={true} />
    <Pricing></Pricing>
  </Fragment>
);

export default Home;
