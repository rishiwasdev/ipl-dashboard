import { React, useEffect, useState } from "react";

import './HomePage.scss'
import { TeamTile } from "../components/TeamTile";
  
export const HomePage = () => {
  const [teams, setTeams] = useState([]);

  useEffect(
    () => {
      const fetchTeams = async () => {
        const response = await fetch(`${process.env.REACT_APP_API_ROOT_URL}/teams`); //http://localhost:8080
        const data = await response.json();
        console.log(data);
        setTeams(data);
      }
      fetchTeams();
    }, [] // without array [], it keeps calling API
  );

  return (
    <div className="HomePage">
      <div className="header-section">
        <h1 className="app-name">IPL Dashboard</h1>
      </div>
      <div className="team-grid">
        {teams.map(team => <TeamTile  key={team.id} teamName={team.teamName} />)}
      </div>
    </div>
  );
}