import { React, useEffect, useState } from "react";
import { useParams } from 'react-router-dom';
import { MatchDetailCard } from "../components/MatchDetailCard";
import { MatchSmallCard } from "../components/MatchSmallCard";

export const TeamPage = () => {
  const [team, setTeam] = useState({ matches: [] });
  const { teamName } = useParams();

  useEffect(
    () => {
      const fetchMatches = async () => {
        const response = await fetch(`http://localhost:8080/teams/${teamName}`);
        const data = await response.json();
        setTeam(data);
      }
      fetchMatches();
    }, [teamName] // call the hook when 'teamName' changes (without array [], it keeps calling API)
  );

  if (!team || !team.teamName) {
    return <h1>Team not found</h1>;
  }

  return (
    <div className="TeamPage">
      <h1>{team.teamName}</h1>
      
       <MatchDetailCard teamName={team.teamName} match={team.matches[0]}/>   {/* ONLY first match REQUIRED for small card */}

      {team.matches.slice(1).map(match => <MatchSmallCard teamName={team.teamName} match={match}/>)}   {/* first match NOT REQUIRED for small card */}
    </div>
  );
}