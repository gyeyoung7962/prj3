import { Box, Input, Stack } from "@chakra-ui/react";
import { useEffect } from "react";

export function BookMovieList({
  checkedTheaterNumber,
  movieLocationAdd,
  onScreenList,
  willScreenList,
  checkedMovieId,
  setCheckedMovieId,
}) {
  useEffect(() => {
    setCheckedMovieId(0);
  }, [checkedTheaterNumber, movieLocationAdd]);

  return (
    <Box>
      <Stack>
        {onScreenList.length > 0 && (
          <Box
            color={"white"}
            bgColor={"darkslategray"}
            opacity={"0.9"}
            h={"50px"}
            fontSize={"larger"}
            fontWeight={"600"}
            textAlign={"center"}
            alignContent={"center"}
          >
            상영중
          </Box>
        )}
        {onScreenList.map((movie) => (
          <Box key={movie.id}>
            <Input
              w={"95%"}
              cursor={"pointer"}
              border={"none"}
              value={movie.title}
              bgColor={checkedMovieId === movie.id ? "lightgray" : ""}
              isDisabled={!movie.theaterNumber.includes(checkedTheaterNumber)}
              onClick={() => {
                setCheckedMovieId(movie.id);
              }}
              readOnly
            />
          </Box>
        ))}
        {willScreenList.length > 0 && (
          <Box
            color={"white"}
            bgColor={"darkslategray"}
            opacity={"0.9"}
            h={"50px"}
            fontSize={"larger"}
            fontWeight={"600"}
            textAlign={"center"}
            alignContent={"center"}
          >
            상영예정
          </Box>
        )}
        {willScreenList.map((movie) => (
          <Box key={movie.id}>
            <Input
              w={"95%"}
              cursor={"pointer"}
              border={"none"}
              value={movie.title}
              bgColor={checkedMovieId === movie.id ? "lightgray" : ""}
              isDisabled={!movie.theaterNumber.includes(checkedTheaterNumber)}
              onClick={() => {
                setCheckedMovieId(movie.id);
              }}
              readOnly
            />
          </Box>
        ))}
      </Stack>
    </Box>
  );
}
