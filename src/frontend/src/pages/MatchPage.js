import { React, useEffect, useState } from "react";
import { MatchDetailCard } from "../components/MatchDetailCard";
import { useParams } from 'react-router-dom';

export const MatchPage = () => {
  const [matches, setMatches] = useState([]);
  const { teamName, year } = useParams();

  useEffect(
    () => {
      const fetchMatches = async () => {
        const response = await fetch(`http://localhost:8080/teams/${teamName}/matches?year=${year}`);
        const data = await response.json();
        setMatches(data);
      }
      fetchMatches();
    }, [teamName, year] // without array [], it keeps calling API
  );

  if (!matches && matches.length === 0) {
    return <h1>Matches detail not found</h1>;
  }
  
  return (
      <div className="MatchPage">
        <h1>Match Page: &emsp; {teamName}</h1>
        {
          matches.map(match => <MatchDetailCard teamName={teamName} match={match} />)
        }
      </div>
    );
}