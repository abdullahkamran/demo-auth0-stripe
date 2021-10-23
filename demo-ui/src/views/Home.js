import React, { Fragment } from "react";

import Hero from "../components/Hero";
import ExternalApi from "./ExternalApi";

const Home = () => (
  <Fragment>
    <Hero />
    <hr />
    <ExternalApi skipToken={true} />
  </Fragment>
);

export default Home;
