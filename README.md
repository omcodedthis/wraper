# wraper
Wraper extracts various details and creates a summary of a webpage. With the use of [Ollama](https://github.com/jmorganca/ollama) & [Docker](https://www.docker.com/), the details parsed is passed to [Orca Mini](https://ollama.ai/library/orca-mini) for analysis. This is Ollama's lightest model with 3B parameters, suitable for systems with at least 8GB of RAM. While more resource-reliant models can also be used, Orca Mini was chosen for the sake of accessbility. 

## Demo
![wraperexample](https://github.com/omcodedthis/wraper/assets/119602009/76bf27b2-b330-4bfc-8822-76ad04bd7361)

Above is an example using [GitHub's Wikipedia page](https://en.wikipedia.org/wiki/GitHub), & the response generated by wraper. After setting up the Ollama Docker image, wraper will automatically run Docker & start the container.

## How does it work?
![wraperarch](https://github.com/omcodedthis/wraper/assets/119602009/85bd666a-7541-4fc3-a048-014201ff3507)

Ollama's Orca Mini does not have the ability to make web searches as it runs locally, as such, `TextGatherer` creates a HTTP request & extracts the text of the webpage. With the use of Generative AI, webpages with large amounts of text can be easily summarized into less than 100 words, allowing users to analyse multiple websites much more swiftly.

Given enough computation power, multiple websites can be summarized simultaneously. A possible application would be for the purposes of learning content that is naturally verbose compared to paying for a subscription to utilise other forms of Generative AI technologies.

## Getting Started
* Install [Docker](https://docs.docker.com/engine/install/) & save it to your `Program Files` folder.

* Run Ollama's Docker Image using these statements (one time setup):
  ```
  docker run -d -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
  ```
  ```
  docker exec -it ollama ollama run orca-mini
  ```
Once the inital set-up is completed, **Docker & the Ollama container will start automatically when wraper is run**. Do note that it may take some time to receive a response, this is entirely dependent on the webpage & your system specification. You can also utilise a dedicated GPU to run Ollama's models by setting up the [NVIDIA Container Toolkit](https://docs.nvidia.com/datacenter/cloud-native/container-toolkit/latest/install-guide.html#installation) for Linux or systems with WSL2.
