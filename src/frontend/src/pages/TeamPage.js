import { React, useEffect, useState } from "react";
import { MatchDetailCard } from "../components/MatchDetailCard";
import { MatchSmallCard } from "../components/MatchSmallCard";

export const TeamPage = () => {
  const [team, setTeam] = useState({matches:[]});

  useEffect(
    ()=>{
      const fetchMatches = async() => {
        const response = await fetch('http://localhost:8080/teams/Delhi Capitals');
        const data = await response.json();
        setTeam(data);
      }
      fetchMatches();
    },[] // without empty array, it keeps calling API.
  );

  return (
    <div className="TeamPage">
      <h1>{team.teamName}</h1>
      
       <MatchDetailCard match={team.matches[0]}/>   {/* ONLY first match REQUIRED for small card */}

      {team.matches.slice(1).map(match => <MatchSmallCard match={match}/>)}   {/* first match NOT REQUIRED for small card */}
    </div>
  );
}